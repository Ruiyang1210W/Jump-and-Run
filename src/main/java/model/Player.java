package model;

import util.Constants;
import util.Vec2;

public class Player {
    public Vec2 pos = new Vec2(); // top-left in px
    public Vec2 vel = new Vec2(); // px/s
    public double w = 24, h = 32; // hitbox


    // state
    public boolean onGround = false;
    private double coyoteTimer = 0;
    private double jumpBufferTimer = 0;
    private boolean jumpHeld = false;


    public Rect bounds(){ return new Rect(pos.x, pos.y, w, h); }


    public void bufferJump(){ jumpBufferTimer = Constants.JUMP_BUFFER; }
    public void setJumpHeld(boolean held){ this.jumpHeld = held; }


    public void tick(Level level, double dt, double moveInput){
        // Horizontal acceleration
        if (Math.abs(moveInput) > 0){
            vel.x += moveInput * Constants.MOVE_ACCEL * dt;
        } else {
        // decelerate towards zero
            if (vel.x > 0){ vel.x = Math.max(0, vel.x - Constants.MOVE_DECEL*dt); }
            else if (vel.x < 0){ vel.x = Math.min(0, vel.x + Constants.MOVE_DECEL*dt); }
        }
        // clamp speed
        if (vel.x > Constants.MAX_RUN_SPEED) vel.x = Constants.MAX_RUN_SPEED;
        if (vel.x < -Constants.MAX_RUN_SPEED) vel.x = -Constants.MAX_RUN_SPEED;


        // Gravity
        vel.y += Constants.GRAVITY * dt;


        // Variable jump height: if jump released while moving up, reduce upward speed
        if (!jumpHeld && vel.y < 0){ vel.y += 2000 * dt; }


        // Timers
        if (onGround) coyoteTimer = Constants.COYOTE_TIME; else coyoteTimer -= dt;
        if (jumpBufferTimer > 0) jumpBufferTimer -= dt;


        // Apply X, resolve collisions axis-aligned
        moveX(level, vel.x * dt);
        // Apply Y
        moveY(level, vel.y * dt);


        // Check jump consumption after movement so landing + buffer works
        if (jumpBufferTimer > 0 && coyoteTimer > 0){
            jump();
            jumpBufferTimer = 0;
        }
}


private void jump(){
    vel.y = Constants.JUMP_VELOCITY;
    onGround = false;
    coyoteTimer = 0;
}


private void moveX(Level level, double dx){
    pos.x += dx;
    Rect r = bounds();
    if (!level.rectHitsSolid(r)) return;
    // resolve into free space (sweep back)
    if (dx > 0){
    // moving right: find nearest tile boundary to the left
        int tx = (int)Math.floor((r.x + r.w) / Constants.TILE_SIZE);
        pos.x = tx * Constants.TILE_SIZE - r.w - 0.001;
    } else if (dx < 0){
        int tx = (int)Math.floor(r.x / Constants.TILE_SIZE);
        pos.x = (tx + 1) * Constants.TILE_SIZE + 0.001;
    }
    vel.x = 0;
}


private void moveY(Level level, double dy){
    pos.y += dy;
    Rect r = bounds();
    if (!level.rectHitsSolid(r)){
        onGround = false;
        return;
    }
    if (dy > 0){
    // moving down, land
        int ty = (int)Math.floor((r.y + r.h) / Constants.TILE_SIZE);
        pos.y = ty * Constants.TILE_SIZE - r.h - 0.001;
        vel.y = 0;
        onGround = true;
    } else if (dy < 0){
    // moving up, hit head
        int ty = (int)Math.floor(r.y / Constants.TILE_SIZE);
        pos.y = (ty + 1) * Constants.TILE_SIZE + 0.001;
        vel.y = 0;
        onGround = false;
    }
    }
}
