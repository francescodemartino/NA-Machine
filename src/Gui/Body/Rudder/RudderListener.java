package Gui.Body.Rudder;

public interface RudderListener
{
    void onClickPlay();

    void onClickPause();

    void onClickStep();

    void onClickStop();

    void onChangeSlider(int level);
}