package uz.pdp.lesson.bot.states.child;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum ArchiveState  implements State{
    CHOOSE_CURRENCY(null),
    ENTER_DATE(CHOOSE_CURRENCY),
    RESULT(ENTER_DATE);

     public ArchiveState pervState;

    ArchiveState(ArchiveState pervState) {
        this.pervState = pervState;
    }
}
