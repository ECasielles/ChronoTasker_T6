package com.example.usuario.chronotasker.mvvm.game;

import android.util.DisplayMetrics;

import com.example.usuario.chronotasker.mvvm.base.OnFragmentActionListener;

import processing.core.PApplet;
import processing.core.PVector;

public class GameSketchFragment extends PApplet implements GameNavigator, OnFragmentActionListener { //

    public static final String TAG = GameSketchFragment.class.getSimpleName();

    private GameViewModel mViewModel;

    private Population population;
    private static final int POPULATION_SIZE = 50;
    private int lifespan = 500;
    private int active = 0;
    private int curframe = 0;
    private float maxforce = 1.5f;
    private float maxSpeed = 5f;
    private float mutationRate = 0.15f;
    private float rx, ry, rw, rh, rx2, ry2;
    private float canvasD;
    private int windowHeight;
    private int windowWidth;
    private PVector target;
    private Rocket bestRocket;



    public GameViewModel makeViewModel() {
        return new GameViewModel();
    }


    public void setViewModel(GameViewModel viewmodel) {
        mViewModel = viewmodel;
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
        super.onBackPressed();
    }


    @Override
    public String getViewModelTag() {
        return GameViewModel.TAG;
    }


    @Override
    public String getFragmentTag() {
        return TAG;
    }


    public void settings() {

        //fullScreen();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windowHeight = displayMetrics.heightPixels;
        windowWidth = displayMetrics.widthPixels;
        size(windowWidth, windowHeight);
    }

    public void setup() {
        //Barriers' position and dimension
        rx = width * 0.1f;
        ry = height * 0.2f;
        rw = width * 0.5f;
        rh = height * 0.02f;
        rx2 = width * 0.4f;
        ry2 = height * 0.4f;
        canvasD = sqrt(width * width + height * height);
        target = new PVector(width / 2.0f, height * 0.1f);
        population = new Population();
        active = POPULATION_SIZE;
    }

    //Loops every frame drawing all elements
    public void draw() {
        background(0);
        population.run();
        curframe++;

        //Checks if all are dead or time limit is reached
        if (curframe == lifespan || active == 0) {
            bestRocket = population.evaluateFitness();
            population.selection();
            curframe = 0;
            active = POPULATION_SIZE;
        }

        //Draws all fixed elements
        fill(255);
        rect(rx, ry, rw, rh);
        rect(rx2, ry2, rw, rh);
        ellipse(target.x, target.y, 16, 16);
    }

    //POPULATION CLASS
    public class Population {
        Rocket[] rockets = new Rocket[POPULATION_SIZE];
        float totalfit = 0f;
        float maxfit = 0f;
        int maxfitID = -1;

        //Constructor
        public Population() {
            for (int i = 0; i < POPULATION_SIZE; i++)
                rockets[i] = new Rocket(null);
        }


        //Called every end of cycle
        //Calculates each rocket's fitness for selection
        //and a total overall of population' fitness
        //Returns the first Rocket with the maximum fitness of the population
        public Rocket evaluateFitness() {
            totalfit = 0f;
            maxfit = 0f;
            maxfitID = -1;
            for (int i = 0; i < POPULATION_SIZE; i++) {
                rockets[i].calcFitness();
                totalfit += rockets[i].fitness;
                if (rockets[i].fitness > maxfit) {
                    maxfit = rockets[i].fitness;
                    maxfitID = i;
                }
            }
            return rockets[maxfitID];
        }


        //Called every end of cycle
        //Calculates gene crossover + mutation from fitness
        //Updates rocket population
        public void selection() {
            Rocket[] childRockets = new Rocket[POPULATION_SIZE];
            for (int i = 0; i < rockets.length; i++) {
                int idA = pickparent();
                int idB = pickparent();
                DNA dnaA = rockets[idA].dna;
                DNA dnaB = rockets[idB].dna;
                float crossoverRateAB = rockets[idA].fitness /
                        (rockets[idA].fitness + rockets[idB].fitness);
                DNA childDNA = dnaA.crossover(dnaB, crossoverRateAB);
                childDNA.mutation();
                childRockets[i] = new Rocket(childDNA);
                //Changes the color to track best ID's children
                if (maxfitID == idA)
                    childRockets[i].setColorBestRocket();
            }
            rockets = childRockets;
        }


        //Picks a random rocket for gene crossover
        //The most fit have exponentially greater chances to
        //be selected here
        //Returns the id of the selected mParent
        public int pickparent() {
            float selectedscore = random(0, totalfit);
            float currentscore = rockets[0].fitness;
            int count = 0;
            while (currentscore < selectedscore) {
                count++;
                currentscore += rockets[count].fitness;
            }
            return count;
        }


        //Updates all rockets' positions and values
        public void run() {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                rockets[i].update();
                rockets[i].show();
            }
        }

    }

    //DNA CLASS
    public class DNA {
        public PVector[] genes = new PVector[lifespan];

        public DNA(PVector[] newGenes) {
            if (newGenes != null) {
                genes = newGenes;
            } else {
                for (int i = 0; i < lifespan; i++) {
                    genes[i] = PVector.random2D();
                    genes[i].setMag(maxforce);
                }
            }
        }
        public DNA crossover(DNA partnerDNA, float ratioAB) {
            PVector[] newgenes = new PVector[lifespan];

            for (int i = 0; i < genes.length; i++)
                newgenes[i] = random(1) < ratioAB ?
                        genes[i] :
                        partnerDNA.genes[i];

            return new DNA(newgenes);
        }
        public void mutation() {
            for (int i = 0; i < genes.length; i++) {
                //TODO: CHECK IF INCLUSIVE
                if (random(0, 1) < mutationRate) {
                    genes[i] = PVector.random2D();
                    genes[i].setMag(maxforce);
                }
            }
        }
    }

    //ROCKET CLASS
    public class Rocket {
        public DNA dna;
        public float fitness;
        PVector pos;
        PVector vel;
        PVector acc;
        boolean completed = false;
        boolean crashed = false;
        float distanceScore = 0;
        int color = color(255, 150);

        public Rocket(DNA newDNA) {
            pos = new PVector(width / 2 * 0.95f, height * 0.95f);
            vel = new PVector();
            acc = new PVector();
            fitness = 0;
            dna = newDNA == null ?
                    new DNA(null) :
                    newDNA;
        }

        public void applyForce(PVector force) {
            acc.add(force);
        }

        //Here's where the magic(?) happens
        public void calcFitness() {
            float d = dist(pos.x, pos.y, target.x, target.y);

            //Map scales a given value (or does inverted scaling)
            distanceScore = map(d, 0, canvasD, 100, 0);

            fitness = crashed ?
                    (float) Math.floor(Math.pow(1.05, distanceScore)) :
                    (float) Math.floor(Math.pow(1.15, distanceScore));
        }

        public void update() {
            if (!completed && !crashed) {
                //Reaching the target
                float d = dist(pos.x, pos.y, target.x, target.y);
                if (d < 10) {
                    completed = true;
                    pos = target;
                }

                //Crashing with obstacles
                if (pos.x > rx && pos.x < rx + rw && pos.y > ry && pos.y < ry + rh)
                    crashed = true;

                if (pos.x > rx2 && pos.x < rx2 + rw && pos.y > ry2 && pos.y < ry2 + rh)
                    crashed = true;

                //Crashing with the walls
                if (pos.x >= width || pos.x < 0 || pos.y >= height || pos.y < 0)
                    crashed = true;

                //Moves
                applyForce(dna.genes[curframe]);

                //Updates movement vectors
                if (!completed && !crashed) {
                    vel.add(acc);
                    pos.add(vel);
                    acc.mult(0);
                    vel.limit(maxSpeed);
                } else {
                    active--;
                }
            }
        }

        public void setColorBestRocket() {
            //this.color = color('hsba(160, 100%, 50%, 0.5)');
            color = color(155,155,255);
        }

        public void show() {
            pushStyle();
            pushMatrix();
            //push();
            noStroke();
            fill(color);
            translate(pos.x, pos.y);
            rotate(vel.heading());
            rectMode(CENTER);
            rect(0, 0, 50, 8);
            //pop();
            popMatrix();
            popStyle();
        }

    }



}

