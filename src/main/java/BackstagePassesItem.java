/**
 *
 */
public class BackstagePassesItem extends Item {
    public BackstagePassesItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void handleQuality() {
        this.setQuality(this.getQuality() + defaultIncrement());
        if(isOverdue()) {
            this.setQuality(0);
        }
        if(isEventPhase()) {
            this.setQuality(this.getQuality() + defaultIncrement());
        }

        if(isLastMinuteEventPhase()) {
            this.setQuality(this.getQuality() + defaultIncrement());
        }

    }

    private boolean isEventPhase() {
        return !isOverdue() && this.getSellIn() < 11;
    }

    private boolean isLastMinuteEventPhase() {
        return !this.isOverdue() && this.getSellIn() < 6;
    }
}
