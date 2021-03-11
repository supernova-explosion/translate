package translate.preference;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import translate.Activator;

public class TranslatePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public TranslatePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public void createFieldEditors() {
		ComboFieldEditor sourceFieldEditor = new ComboFieldEditor(PreferenceConstants.SOURCE, "源语言：",
				new String[][] { { "自动检测", "auto" }, { "中文", "zh" }, { "英语", "en" }, { "日语", "jp" }, { "韩语", "kor" },
						{ "法语", "fra" }, { "俄语", "ru" } },
				getFieldEditorParent());
		addField(sourceFieldEditor);
		ComboFieldEditor targetFieldEditor = new ComboFieldEditor(PreferenceConstants.TARGET, "目标语言：", new String[][] {
				{ "英语", "en" }, { "中文", "zh" }, { "日语", "jp" }, { "韩语", "kor" }, { "法语", "fra" }, { "俄语", "ru" } },
				getFieldEditorParent());
		addField(targetFieldEditor);
		addField(new StringFieldEditor(PreferenceConstants.APP_ID, "百度翻译APP_ID：", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.SECRET_KEY, "百度翻译SECRET_KEY：", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}

}