package com.example.sohan.currencyconvertor.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.sohan.currencyconvertor.R;


public class FragmentHelper {

    /**
     * Clears all fragment if any in the backStack (clears immediately)
     *
     * @return is fragment popped or not
     */
    public static boolean clearAllFragmentFromBackStackImmediate(FragmentManager fragmentManager) {
        boolean isPopped = false;
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            try {
                isPopped = fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        return isPopped;
    }

    /**
     * Clears all fragment if any in the backStack
     */
    public static void clearAllFragmentFromBackStack(FragmentManager fragmentManager) {
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            try {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pops fragment if any in the backStack (pops immediately)
     *
     * @return is fragment popped or not
     */
    public static boolean popBackStackImmediate(FragmentManager fragmentManager) {
        boolean isFragmentPresentToPop = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            isFragmentPresentToPop = true;
            LogUtils.LOGE("back stack entry", fragmentManager.getBackStackEntryCount() + "");
            fragmentManager.popBackStackImmediate();
        }
        return isFragmentPresentToPop;
    }

    /**
     * Pops fragment if any in the backStack (pops in background)
     */
    public static void popBackStack(FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Find fragment by the TAG used during adding the Fragment
     *
     * @param fragmentManager supportFragmentManager
     * @param tag             Tag of the fragment to find
     */
    public static android.support.v4.app.Fragment findFragmentByTag(FragmentManager fragmentManager, String tag) {
        if (fragmentManager != null) {
            return fragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

    /**
     * Add a Fragment to a Container
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to add
     */
    public static void addFragment(FragmentManager fragmentManager, int containerViewId, Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left
                    , R.anim.slide_in_from_left, R.anim.exit_to_right);
            fragmentTransaction = fragmentTransaction.add(containerViewId, fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Add a Fragment to a Container without animation
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to add
     */
    public static void addFragmentWithoutAnimation(FragmentManager fragmentManager, int containerViewId, Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction = fragmentTransaction.add(containerViewId, fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Replace a Fragment in a Container, doesn't support backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     */
    public static void replaceFragmentWithStateLoss(FragmentManager fragmentManager, int containerViewId
            , Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left);
            fragmentTransaction = fragmentTransaction.replace(containerViewId, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * Replace a Fragment in a Container, doesn't support backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     * @param fragmentTag     fragment tag
     */
    public static void replaceFragmentWithStateLoss(FragmentManager fragmentManager, int containerViewId
            , Fragment fragment, String fragmentTag) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left);
            fragmentTransaction = fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * Replace a Fragment in a Container, doesn't support backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     */
    public static void replaceFragment(FragmentManager fragmentManager, int containerViewId,
                                       String fragmentTag, Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.exit_to_left
                    , R.anim.slide_in_from_left, R.anim.exit_to_right);
            fragmentTransaction = fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
            fragmentTransaction.commit();
        }
    }


    /**
     * Replace a Fragment in a Container adds fragment to backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     * @param backStackTag    backStack Tag
     * @param fragmentTag     fragment Tag
     */
    public static void replaceFragment(FragmentManager fragmentManager, int containerViewId
            , Fragment fragment, String backStackTag, String fragmentTag) {
        replaceFragment(fragmentManager, containerViewId, fragment, backStackTag, fragmentTag
                , R.anim.slide_in_from_right, R.anim.exit_to_left, R.anim.slide_in_from_left
                , R.anim.exit_to_right);

    }

    /**
     * Replace a Fragment in a Container adds fragment to backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     * @param backStackTag    backStack Tag
     * @param fragmentTag     fragment Tag
     * @param enterAnim       resource id of enter anim, can be null
     * @param exitAnim        resource id of exit anim, can be null
     * @param popEnterAnim    resource id of popeEnter anim, can be null
     * @param popExitAnim     resource id of popExit anim, can be null
     */
    public static void replaceFragment(FragmentManager fragmentManager, int containerViewId
            , Fragment fragment, String backStackTag, String fragmentTag, int enterAnim, int exitAnim
            , int popEnterAnim, int popExitAnim) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
            fragmentTransaction = fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(backStackTag).commit();
            fragmentManager.executePendingTransactions();
        }
    }

    /**
     * Replace a Fragment in a Container doesn't support backStack
     *
     * @param fragmentManager support Fragment Manager
     * @param containerViewId id of the container view where fragment should be added
     * @param fragment        fragment to replace
     * @param fragmentTag     fragment Tag
     * @param enterAnim       resource id of enter anim, can be null
     * @param exitAnim        resource id of exit anim, can be null
     * @param popEnterAnim    resource id of popeEnter anim, can be null
     * @param popExitAnim     resource id of popExit anim, can be null
     */
    public static void replaceFragmentWithoutBackstack(FragmentManager fragmentManager, int containerViewId
            , Fragment fragment, String fragmentTag, int enterAnim, int exitAnim
            , int popEnterAnim, int popExitAnim) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
            fragmentTransaction = fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    /**
     * Get current visible fragment
     *
     * @param fragmentManager supportFragmentManager
     * @return fragment
     */
    public static Fragment getCurrentVisibleFragment(FragmentManager fragmentManager, int containerId) {
        return fragmentManager.findFragmentById(containerId);
    }
}
