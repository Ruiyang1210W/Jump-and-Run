package model;

import util.Constants;


public class Level {
    public final int width, height; // in tiles
    public final int[][] tiles; // tile codes


    public Level(int width, int height){
        this.width = width; this.height = height;
        this.tiles = new int[height][width];
    }


    public Tile getTileAt(int tx, int ty){
        // Left wall, right wall, ceiling = solid
        if (tx < 0 || tx >= width || ty < 0) return Tile.SOLID;

        // BELOW the level = empty space (so the player can fall)
        if (ty >= height) return Tile.EMPTY;

        // Otherwise, return the actual tile
        return Tile.fromCode(tiles[ty][tx]);
    }

    public Tile getCurrentTile(Rect r){
        int left = (int)Math.ceil(r.x / Constants.TILE_SIZE);
        int right = (int)Math.ceil((r.x + r.w) / Constants.TILE_SIZE);
        int top = (int)Math.floor(r.y / Constants.TILE_SIZE);
        int bottom = (int)Math.ceil((r.y + r.h) / Constants.TILE_SIZE);

        for (int ty = top; ty <= bottom; ty++){
            for (int tx = left-1; tx < right; tx++){
                Tile current = getTileAt(tx, ty);
                if (current != Tile.EMPTY){
                    if(current != Tile.SOLID){
                        return getTileAt(tx, ty);
                    }
                }
            }
        }
        return Tile.EMPTY;
    }


    public boolean rectHitsSolid(Rect r){
        int left = (int)Math.floor(r.x / Constants.TILE_SIZE);
        int right = (int)Math.floor((r.x + r.w) / Constants.TILE_SIZE);
        int top = (int)Math.floor(r.y / Constants.TILE_SIZE);
        int bottom = (int)Math.floor((r.y + r.h) / Constants.TILE_SIZE);
        for (int ty = top; ty <= bottom; ty++){
            for (int tx = left; tx <= right; tx++){
                if (getTileAt(tx, ty).solid) return true;
            }
        }
        return false;
    }


    public boolean rectHitsHazard(Rect r){
        int left = (int)Math.floor(r.x / Constants.TILE_SIZE);
        int right = (int)Math.floor((r.x + r.w) / Constants.TILE_SIZE);
        int top = (int)Math.floor(r.y / Constants.TILE_SIZE);
        int bottom = (int)Math.floor((r.y + r.h) / Constants.TILE_SIZE);
        for (int ty = top; ty <= bottom; ty++){
            for (int tx = left; tx <= right; tx++){
                if (getTileAt(tx, ty).hazard) return true;
            }
        }
        return false;
    }
}
