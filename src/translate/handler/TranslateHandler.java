package translate.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import translate.preference.PreferenceConstants;
import translate.util.ActivatorUtil;
import translate.util.TransApi;

public class TranslateHandler extends AbstractHandler {

	protected static final Control StyledText = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String appId = ActivatorUtil.getPreferenceValue(PreferenceConstants.APP_ID);
		String secretKey = ActivatorUtil.getPreferenceValue(PreferenceConstants.SECRET_KEY);
		if (isEmpty(appId) || isEmpty(secretKey)) {
			MessageDialog.openInformation(window.getShell(), "提示",
					"请先配置翻译插件参数！\nWindow -> Preferences -> Translate Preferences");
			return null;
		}
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!(selection instanceof TextSelection)) {
			return null;
		}
		TextSelection textSelection = (TextSelection) selection;
		String text = textSelection.getText();
		if (isEmpty(text)) {
			return null;
		}
		String result = translate(text);
		JSONObject json = JSON.parseObject(result);
		JSONArray jsonArray = json.getJSONArray("trans_result");
		StringBuilder dstBuilder = new StringBuilder();
		jsonArray.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			String dst = unicodeToString(obj.getString("dst"));
			dstBuilder.append(dst).append("\n");
		});
		PopupDialog popupDialog = new PopupDialog(window.getShell(), PopupDialog.HOVER_SHELLSTYLE, true, false, false,
				false, false, dstBuilder.toString(), "") {
			@Override
			protected Point getInitialLocation(Point initialSize) {
				Control control = HandlerUtil.getActiveEditor(event).getAdapter(Control.class);
				StyledText styledText = (StyledText) control;
				Point point = styledText.toDisplay(styledText.getLocationAtOffset(styledText.getCaretOffset()));
				return new Point(point.x + 2, point.y);
			}
		};
		popupDialog.open();
		return null;
	}

	private String translate(String text) {
		TransApi api = new TransApi(ActivatorUtil.getPreferenceValue(PreferenceConstants.APP_ID),
				ActivatorUtil.getPreferenceValue(PreferenceConstants.SECRET_KEY));
		return api.getTransResult(text, ActivatorUtil.getPreferenceValue(PreferenceConstants.SOURCE),
				ActivatorUtil.getPreferenceValue(PreferenceConstants.TARGET));
	}

	private String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	private boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

}
