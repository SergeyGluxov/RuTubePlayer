package info.glukhov.rutube;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class EndscreenView extends LinearLayout implements View.OnClickListener {

    private Button repeatButton;

    public IEndscreenViewListener endscreenListener;

    public EndscreenView(Context context) {
        this(context, null);
    }

    public EndscreenView(Context context, AttributeSet attrs) {

        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.endscreen_view, this);

        setVisibility(View.INVISIBLE);

        setBackgroundColor(Color.DKGRAY & 0x66FFFFFF);

        repeatButton = (Button)this.findViewById(R.id.replayButton);
        repeatButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if( endscreenListener == null )
            return;

       if( id == R.id.replayButton) {
            endscreenListener.onReplayClick();
        }

    }

    //
    public interface IEndscreenViewListener {
        void onReplayClick();
    }

}
