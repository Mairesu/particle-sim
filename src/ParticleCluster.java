public class ParticleCluster {

    private int particles;
    private int particleMax;
    private Location location;

    public ParticleCluster(int startingParticles, int particleMax, Location location)   {
        if(startingParticles > particleMax) {
            this.particles = particleMax;
        }
        else {
            this.particles = startingParticles;
        }
        this.particleMax = particleMax;
        this.location = location;
    }

    public void increaseParticles() {
        if(particles < particleMax) {
            this.particles++;
        }
    }

    public void decreaseParticles() {
        if(particles > 0) {
            this.particles--;
        }
    }

    public void increaseParticlesBy(int increase) {
        if(particles < particleMax) {
            if((particles + increase) < particleMax){
                this.particles = this.particles + increase;
            }
            else {
                this.particles = this.particleMax;
            }
        }
    }

    public void decreaseParticlesBy(int decrease) {
        if(particles > 0
            && ((particles - decrease) > 0)) {
            this.particles = this.particles - decrease;
        }
    }

    public void setLocation(Location newLocation)   {
        this.location = newLocation;
    }

    public Location getLocation()   {
        return this.location;
    }

    public int getParticles()   {
        return this.particles;
    }

    public int getParticleMax() {
        return this.particleMax;
    }
}
