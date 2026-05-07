package com.christophercarrillo.drop2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture pilotaTexture;

    private float posx, posy;
    private float velx, vely;

    private final float MIDA_PILOTA = 1f;

    @Override
    public void create() {
        batch = new SpriteBatch();

        viewport = new FitViewport(8, 5);

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillCircle(50, 50, 50);
        pilotaTexture = new Texture(pixmap);
        pixmap.dispose();

        // Posició inicial
        posx = 0f;
        posy = 0f;

        // Velocitat
        velx = 2f;
        vely = 1.5f;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Actualitzar posició
        posx += velx * delta;
        posy += vely * delta;

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        if (posx <= 0) {
            posx = 0;
            velx *= -1;
        }

        if (posx + MIDA_PILOTA >= worldWidth) {
            posx = worldWidth - MIDA_PILOTA;
            velx *= -1;
        }

        if (posy <= 0) {
            posy = 0;
            vely *= -1;
        }

        if (posy + MIDA_PILOTA >= worldHeight) {
            posy = worldHeight - MIDA_PILOTA;
            vely *= -1;
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(pilotaTexture, posx, posy, MIDA_PILOTA, MIDA_PILOTA);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        pilotaTexture.dispose();
    }
}
