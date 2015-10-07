package name.walnut.kanjian.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import name.walnut.kanjian.app.R;

public class SpanTextView extends TextView {

	private static final String TAG = SpanTextView.class.getSimpleName();
    private int spanColor = Color.GRAY;

	private Context mContext;

	public SpanTextView(Context context) {
		super(context);
		mContext = context;
	}

	public SpanTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpanTextView, 0, 0);

        spanColor = a.getColor(R.styleable.SpanTextView_spanColor, spanColor);

        a.recycle();

    }

	private int mStart = -1;

	private int mEnd = -1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		// Log.e(TAG, "touch event is: " + event.getActionMasked()
		// + " result is: " + result);

		int action = event.getAction();

		// if (action == MotionEvent.ACTION_UP || action ==
		// MotionEvent.ACTION_DOWN) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		x -= getTotalPaddingLeft();
		y -= getTotalPaddingTop();

		x += getScrollX();
		y += getScrollY();

		Layout layout = getLayout();
		int line = layout.getLineForVertical(y);
		int off = layout.getOffsetForHorizontal(line, x);

		CharSequence text = getText();
		if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
			return result;
		}

		Spannable buffer = (Spannable) text;
		ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

		if (link.length != 0) {

			if (action == MotionEvent.ACTION_DOWN) {

				mStart = buffer.getSpanStart(link[0]);
				mEnd = buffer.getSpanEnd(link[0]);

				if (mStart >= 0 && mEnd >= mStart) {
					buffer.setSpan(new BackgroundColorSpan(spanColor), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {

				if (mStart >= 0 && mEnd >= mStart) {
					buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					mStart = -1;
					mEnd = -1;
				}
			}

			return true;
		} else {
			if (mStart >= 0 && mEnd >= mStart) {
				buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				mStart = -1;
				mEnd = -1;
			}

			Selection.removeSelection(buffer);
			return false;
		}
		// }

		// return false;
	}

	@Override
	public boolean hasFocusable() {
		return false;
	}

	public static class ClickSpan extends ClickableSpan {

		@Override
		public void onClick(View arg0) {
			Log.e(TAG, "clickableSpan onClick.");
			Spannable spannable = (Spannable) ((TextView) arg0).getText();
			Selection.removeSelection(spannable);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setUnderlineText(false);
			ds.setColor(Color.BLUE);
		}
	}
}
