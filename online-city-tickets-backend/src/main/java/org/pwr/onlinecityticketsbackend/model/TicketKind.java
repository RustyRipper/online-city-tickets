package org.pwr.onlinecityticketsbackend.model;

public enum TicketKind {
    STANDARD {
        @Override
        public double getDiscountPercent() {
            return 0.0;
        }
    },
    REDUCED {
        @Override
        public double getDiscountPercent() {
            return 50.0;
        }
    };

    public abstract double getDiscountPercent();
}
