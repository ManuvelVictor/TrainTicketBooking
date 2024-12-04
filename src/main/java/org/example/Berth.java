package org.example;

import org.example.model.Ticket;

public class Berth {
    private int lb = 21;
    private int mb = 21;
    private int ub = 21;

    public void allocateBerth(Ticket ticket) {
        switch (ticket.getBerthPreference()) {
            case "LB" -> {
                if (lb > 0) {
                    lb--;
                    ticket.setConfirmationStatus("Booked LB");
                    return;
                }
            }
            case "MB" -> {
                if (mb > 0) {
                    mb--;
                    ticket.setConfirmationStatus("Booked MB");
                    return;
                }
            }
            case "UB" -> {
                if (ub > 0) {
                    ub--;
                    ticket.setConfirmationStatus("Booked UB");
                    return;
                }
            }
        }

        if (lb > 0) {
            lb--;
            ticket.setConfirmationStatus("Booked LB");
        } else if (mb > 0) {
            mb--;
            ticket.setConfirmationStatus("Booked MB");
        } else {
            ub--;
            ticket.setConfirmationStatus("Booked UB");
        }
    }
}