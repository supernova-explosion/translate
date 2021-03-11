package translate.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import translate.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.SOURCE, "auto");
		store.setDefault(PreferenceConstants.TARGET, "zh");
		store.setDefault(PreferenceConstants.APP_ID, "");
		store.setDefault(PreferenceConstants.SECRET_KEY, "");
	}

}
