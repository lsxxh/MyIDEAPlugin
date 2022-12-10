package com.github.lsxxh.myideaplugin.helper

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationGroupEP
import com.intellij.openapi.project.Project
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.NOTIFICATION_GROUP
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.notificationGroup

class NotificationHelper {
    companion object
    {
        @JvmStatic
        fun showRightBelowBalloon(msg: String, project: Project) {
            //notificationGroup.createNotification(msg, NotificationType.WARNING)
            NotificationGroupManager.getInstance().getNotificationGroup("Custom Notification Group INFORMATION")
                    .createNotification(msg, NotificationType.INFORMATION)
                    .notify(project)

            //@Deprecated("Use com.intellij.notification.impl.NotificationGroupEP and com.intellij.notification.NotificationGroupManager")
            //  @ApiStatus.ScheduledForRemoval(inVersion = "2021.3")
            //  constructor(displayId: String,
            //              displayType: NotificationDisplayType,
            //              isLogByDefault: Boolean = true,
            //              toolWindowId: String? = null,
            //              icon: Icon? = null,
            //              @NotificationTitle title: String? = null,
            //              pluginId: PluginId? = null) :
            //    this(displayId = displayId, displayType = displayType, isLogByDefault = isLogByDefault, toolWindowId = toolWindowId, icon = icon,
            //         title = title, pluginId = pluginId, registerGroup = true)
        }
    }
}