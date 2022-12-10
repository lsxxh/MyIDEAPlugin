package action;

import com.example.plugindemo.model.ActionPerformer;
import com.github.lsxxh.myideaplugin.helper.FormatTools;
import com.github.lsxxh.myideaplugin.helper.NotificationHelper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;

import java.util.Objects;

public class PxToDpAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        NotificationHelper.showRightBelowBalloon("px->dp start", Objects.requireNonNull(anActionEvent.getProject()));
        //成功调用formatNearCode测试将: xx="5px=>xx="50dp
        FormatTools.formatNearCode(new ActionPerformer(anActionEvent.getProject(), anActionEvent.getRequiredData(CommonDataKeys.EDITOR)));
    }
}
