package com.scripty.base;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class CustomNavigationView extends NavigationView {


    private int selectedItemId;
    public CustomNavigationView(@NonNull Context context) {
        super(context);
    }

    public CustomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    // overrides

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        selectedItemId = item.getItemId();
//
//        if(mManager.isStateSaved())
//            return false;
//        else{
//
//        }
//        return false;
//    }

    // custom methods
   private FragmentManager mManager;
    private String selectedItemTag;
    private boolean isOnFirstFragment;
    private int firstFragmentGraphId  ;
    public LiveData setupWithNavController(NavigationView test, List<Integer> navGraphIds, final FragmentManager fragmentManager, int containerId, Intent intent) {
        mManager = fragmentManager;
        final SparseArray<String> graphIdToTagMap = new SparseArray<>();

        final MutableLiveData selectedNavController = new MutableLiveData<NavController>();

        firstFragmentGraphId = 0 ;
        int index=0;
        for( int id : navGraphIds){
            String fragmentTag = getFragmentTag(id);
            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager,
                    fragmentTag,id,containerId);

            int graphId = navHostFragment.getNavController().getGraph().getId();

            if(index==0){
                firstFragmentGraphId = graphId;
            }

            graphIdToTagMap.put(graphId,fragmentTag);
            if (selectedItemId == graphId){
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fragmentManager,navHostFragment, index==0);
            } else {
                detachNavHostFragment(fragmentManager,navHostFragment);
            }
            index++;
        }

        selectedItemTag = graphIdToTagMap.get(selectedItemId);
        final String firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);

        this.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean mustReturn;
                if (fragmentManager.isStateSaved())
                    mustReturn = false;
                else{
                    String newlySelectedItemTag = (String)graphIdToTagMap.get(item.getItemId());
                    if (selectedItemTag != newlySelectedItemTag){
                        fragmentManager.popBackStack(firstFragmentTag,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        NavHostFragment selectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(newlySelectedItemTag);

                        if(firstFragmentTag != newlySelectedItemTag){
                            FragmentTransaction transaction_ = fragmentManager.beginTransaction().setCustomAnimations(
                                    R.anim.nav_default_enter_anim,
                                    R.anim.nav_default_exit_anim,
                                    R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim)
                                    .attach(selectedFragment)
                                    .setPrimaryNavigationFragment(selectedFragment);

                            for(int i =0;i <graphIdToTagMap.size();i++){
                                if (!graphIdToTagMap.get(i).equals(newlySelectedItemTag)){
                                    Fragment f = fragmentManager.findFragmentById(firstFragmentGraphId);
                                    if (f!=null)
                                        transaction_.detach(f);
                                }
                            }
                            transaction_.addToBackStack(firstFragmentTag)
                                    .setReorderingAllowed(true)
                                    .commit();
                        }
                        selectedItemTag = newlySelectedItemTag;
                        isOnFirstFragment = selectedItemTag.equals(firstFragmentTag);
                        selectedNavController.setValue(selectedFragment.getNavController());
                        mustReturn = true;

                    } else
                        mustReturn = false;
                }
                return mustReturn;
            }
        });

        setupDeepLinks(navGraphIds, fragmentManager, containerId, intent);
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (!isOnFirstFragment && !isOnBackStack(fragmentManager,firstFragmentTag))
                    selectedItemId = firstFragmentGraphId;

                NavController nav = (NavController) selectedNavController.getValue();
                if(nav!=null){
                    NavController tmp = nav;
                    if(tmp.getCurrentDestination() == null){
                        NavGraph graph_ = tmp.getGraph();
                        tmp.navigate(graph_.getId());
                    }
                }
            }
        });

        return (LiveData) selectedNavController;

    }

    // helpers
    private static boolean isOnBackStack(FragmentManager manager, String backStackName ) {
        int backStackCount = manager.getBackStackEntryCount();
        int index=0;
        for (int i= backStackCount; index < i ; index++) {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(index);
            if (entry.getName() == backStackName)
                return true;
        }
        //        val backStackCount = backStackEntryCount
//        for (index in 0 until backStackCount) {
//            if (getBackStackEntryAt(index).name == backStackName) {
//                return true
//            }
//        }
        return false;
    }

    private void setupDeepLinks(List<Integer> navGraphIds ,FragmentManager fragmentManager, int containerId,Intent intent) {

        int index =0;
        for(index = 0 ; index < navGraphIds.size() ; index++){
            String fragmentTag = getFragmentTag(index);

            int navGraphId = navGraphIds.get(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager,fragmentTag,navGraphId,containerId);
            if (navHostFragment.getNavController().handleDeepLink(intent) &&
                selectedItemId != navHostFragment.getNavController().getGraph().getId()){
                selectedItemId = navHostFragment.getNavController().getGraph().getId();
            }
        }

    }

    private NavHostFragment obtainNavHostFragment(
            FragmentManager fragmentManager ,
            String fragmentTag,
            int navGraphId,
            int containerId){
        // If the Nav Host fragment exists, return it
        NavHostFragment existingFragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if ( existingFragment != null )
            return existingFragment;


        // Otherwise, create it and return it.
        NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);
        fragmentManager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow();
        return navHostFragment;
    }

    private String getFragmentTag(int index) {
       return "bottomNavigation#"+index;
    }

    private void attachNavHostFragment(
            FragmentManager fragmentManager ,
            NavHostFragment navHostFragment  ,
            boolean isPrimaryNavFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction().attach(navHostFragment);

            if (isPrimaryNavFragment) {
                transaction.setPrimaryNavigationFragment(navHostFragment);
            }

        transaction.commitNow();

    }

    private void detachNavHostFragment(FragmentManager fragmentManager ,NavHostFragment navHostFragment    ) {
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }



}
