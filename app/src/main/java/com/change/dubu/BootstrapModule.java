package com.change.dubu;

import android.accounts.AccountManager;
import android.content.Context;

import com.change.dubu.authenticator.BootstrapAuthenticatorActivity;
import com.change.dubu.authenticator.LogoutService;
import com.change.dubu.core.CheckIn;
import com.change.dubu.core.TimerService;
import com.change.dubu.ui.*;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                TimerService.class,
                BookListFragment.class,
                BookActivity.class,
                BookListFragment.class,
                ChangeActivity.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
