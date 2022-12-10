package action;

import com.github.lsxxh.myideaplugin.helper.NotificationHelper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.util.Objects;

public class PxToDpAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        NotificationHelper.showRightBelowBalloon("px->dp start", Objects.requireNonNull(e.getProject()));
    }
}
