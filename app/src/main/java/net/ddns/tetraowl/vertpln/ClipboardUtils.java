package net.ddns.tetraowl.vertpln;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils {
    public static void copyToClipboard(Context context, CharSequence label, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label,text);
        clipboard.setPrimaryClip(clip);
    }

}
