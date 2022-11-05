public interface Animates extends  Entity_I{
    default double getAnimationPeriod() {
        switch (this.getKind()) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return this.getAnimationPeriod();
            default:
                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getKind()));
        }
    }

    default Action createAnimationAction( int repeatCount) {
        return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
    }


}
