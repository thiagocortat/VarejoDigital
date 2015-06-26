package com.varejodigital.fragments.base;


import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.varejodigital.R;
import com.varejodigital.interfaces.OnLayoutInjectListener;
import com.varejodigital.utilities.StringUtils;

import java.lang.reflect.Field;


import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;


public abstract class BaseFragment extends Fragment implements OnLayoutInjectListener {
	
	protected View newView;
	protected View mEmptyView;
    protected View mEmptyImageView;
    protected View mEmptyContainer;
	protected View mProgressContainer;
    protected String mProgressText;

    protected CircularProgressBar mCircularProgressBar;
//    protected AnimationDrawable myAnimationDrawable;
	private boolean mIsContentEmpty;
//    protected OnLayoutInjectListener onLayoutInjectListener;
    private Handler mHandler_ = new Handler(Looper.getMainLooper());



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View contentView_ = super.onCreateView(inflater, container, savedInstanceState);

        if (contentView_ == null && getLayoutResource() != 0) {
            contentView_ = inflater.inflate(getLayoutResource(), container, false);
            onBeforeInjectViews(savedInstanceState);

            ButterKnife.inject(this, contentView_);

//            onAfterInjectViews(savedInstanceState);
        }

        return contentView_;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view != null && getLayoutResource() != 0) {

            onAfterInjectViews(savedInstanceState);
        }
    }

    public void showProgress(){
		try {
		
			if(newView == null){
				
				newView = getActivity().getLayoutInflater().inflate(R.layout.fragment_progress, null, false);
                newView.setEnabled(true);
                newView.setActivated(true);
                newView.setClickable(true);
                assert ((ViewGroup) getView()) != null;
                ((ViewGroup) getView()).addView(newView);
                ((ViewGroup) getView()).bringChildToFront(newView);
				 
				mProgressContainer = newView.findViewById(R.id.progress_container);
                TextView progressTextView = (TextView) newView.findViewById(R.id.progress_text);

                if (!StringUtils.isEmpty(mProgressText)) {
                    progressTextView.setText(mProgressText);
                    progressTextView.setVisibility(View.VISIBLE);
//                    FontsUtil.setRobotoFont(getActivity(), progressTextView);
                }
                else {
                    progressTextView.setVisibility(View.GONE);
                }

                mCircularProgressBar = (CircularProgressBar) newView.findViewById(R.id.my_animation);
                ((CircularProgressDrawable)mCircularProgressBar.getIndeterminateDrawable()).start();

                mEmptyContainer = newView.findViewById(R.id.emptyContainer);
                mEmptyImageView = newView.findViewById(R.id.imageEmpty);
				mEmptyView = newView.findViewById(android.R.id.empty);
                if (mEmptyContainer != null) {
                    mEmptyContainer.setVisibility(View.GONE);
                    setContentEmpty(false);
                }
			}
            else if (mIsContentEmpty) {
                setContentEmpty(false);
            }
			
		} catch (Exception e) {
            e.printStackTrace();
        }

		
	}
	
	public void hideProgress() {
		
		try {
			if(newView != null  && !mIsContentEmpty){
				((ViewGroup) getView()).removeView(newView);
				newView = null;
			}
		} catch (Exception e) {
            newView = null;
        }
		
	}

    public void hideProgressAfterSecond(){
        try {
            mHandler_.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            }, 1000L);

        }catch (Exception e) {}
    }

	  /**
     * The default content for a ProgressFragment has a TextView that can be shown when
     * the content is empty {@link #setContentEmpty(boolean)}.
     * If you would like to have it shown, call this method to supply the text it should use.
     *
     * @param resId Identification of string from a resources
     * @see #setEmptyText(CharSequence)
     */
    public void setEmptyText(int resId) {
        setEmptyText(getString(resId));
    }

    /**
     * The default content for a ProgressFragment has a TextView that can be shown when
     * the content is empty {@link #setContentEmpty(boolean)}.
     * If you would like to have it shown, call this method to supply the text it should use.
     *
     * @param text Text for empty view
     * @see #setEmptyText(int)
     */
    public void setEmptyText(CharSequence text) {
    	//showProgress();
        if (mEmptyView != null && mEmptyView instanceof TextView) {
            ((TextView) mEmptyView).setText(text);

        } else {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
    }
    
    /**
     * If the content is empty, then set true otherwise false. The default content is not empty.
     * You can't call this method if the content view has not been initialized before
     * {@link #setContentEmpty(boolean isEmpty)} and content view not null.
     *
     * @param isEmpty true if content is empty else false
     * @see #isContentEmpty()
     */
    public void setContentEmpty(boolean isEmpty) {
//    	showProgress();
        if (newView == null) {
            throw new IllegalStateException("Content view must be initialized before");
        }
        if (isEmpty) {
            newView.setClickable(false);
            mEmptyContainer.setVisibility(View.VISIBLE);
            mProgressContainer.setVisibility(View.GONE);
        } else {
            newView.setClickable(true);
            mEmptyContainer.setVisibility(View.GONE);
            mProgressContainer.setVisibility(View.VISIBLE);
        }
        mIsContentEmpty = isEmpty;
    }
	
    /**
     * Returns true if content is empty. The default content is not empty.
     *
     * @return true if content is null or empty
     * @see #setContentEmpty(boolean)
     */
    public boolean isContentEmpty() {
        return mIsContentEmpty;
    }


    /**
     * Set the ActionBar title
     *
     * @param title with string of title
     */
    public void setActionBarTitle(String title){
        getActivity().getActionBar().setTitle(title);
    }

    /**
     * Set the ActionBar title
     *
     * @param resTitle with id of title
     */
    public void setActionBarTitle(int resTitle){
        getActivity().getActionBar().setTitle(resTitle);
    }

    /**
     * Set the ActionBar subtitle
     *
     * @param subtitle with string of title
     */
    public void setActionBarSubtitle(String subtitle){
        getActivity().getActionBar().setSubtitle(subtitle);
    }

    /**
     * Set the ActionBar subtitle
     *
     * @param resSubtitle with id of subtitle
     */
    public void setActionBarSubtitle(int resSubtitle){
        getActivity().getActionBar().setSubtitle(resSubtitle);
    }

    public void invalidateOptionsMenu(){
        try {
            getActivity().invalidateOptionsMenu();
        }catch (NullPointerException e){}

    }

    public void setProgressText(String text) {

        mProgressText = text;
    }

    protected void showSimpleDialog(String message) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name);
            builder.setMessage(message);
            builder.setNeutralButton("OK",null);
            AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e) {}
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Bug childFragmentManager on Support Class
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
        }catch (Exception e) {}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public int getLayoutResource() {
        return 0;
    }

    public void onBeforeInjectViews(Bundle savedInstanceState) {}

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {

    }
}
