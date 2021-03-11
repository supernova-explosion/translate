package translate.util;

import org.eclipse.jface.preference.IPreferenceStore;

import translate.Activator;

public class ActivatorUtil {

	public static String getPreferenceValue(String name) {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(name);
	}

}
