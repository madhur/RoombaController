package cleanitnow.com.roombacontroller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import cleanitnow.com.roombacontroller.controller.RoombaController;
import cleanitnow.com.roombacontroller.views.MainFragment;
import cleanitnow.com.roombacontroller.views.RoombaViewFragment;


public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener
{
    private RoombaController roombaController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_dropdown_item);

        getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);



        roombaController=new RoombaController();

    }


    public RoombaController getRoombaController()
    {
        return roombaController;
    }


    @Override
    public boolean onNavigationItemSelected(int position, long id)
    {
        if(position==0)
        {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new MainFragment()).commit();
            return true;
        }
        else if(position ==1)
        {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new RoombaViewFragment()).commit();
            return true;
        }

        return false;

    }
}
