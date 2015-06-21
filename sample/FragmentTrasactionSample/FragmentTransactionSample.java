

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
		//設定轉場動畫 fade in, fade out
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        switch (v.getId()) {
            case R.id.btnLeft:
                ft.replace(R.id.frameLayout, new MainActivityFragment(), null);
                break;
            case R.id.btnRight:
                ft.replace(R.id.frameLayout, new TwoFragment(), null);
                break;
        }
        ft.commit();
    }

