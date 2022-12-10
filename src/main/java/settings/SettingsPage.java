package settings;

import com.github.lsxxh.myideaplugin.settings.PreferenceVariant;

import javax.swing.*;

/**与.form一同放在java目录下
 * or java.lang.ClassNotFoundException: com.github.lsxxh.myideaplugin.settings.SettingsPage PluginClassLoader(...
 */
public class SettingsPage {
    private JLabel Scale;
    private JLabel PresetDensityScale;
    private JSpinner ScaleSpinner;
    private JPanel RootPanel;

    public JLabel getScale() {
        return Scale;
    }

    public JLabel getPresetDensityScale() {
        return PresetDensityScale;
    }

    public JSpinner getScaleSpinner() {
        return ScaleSpinner;
    }

    public JPanel getRootPanel() {
        presetConfig();
        return RootPanel;
    }

    private void presetConfig() {
        PreferenceVariant.INSTANCE.setPresetScale(0.5F);
    }

}
