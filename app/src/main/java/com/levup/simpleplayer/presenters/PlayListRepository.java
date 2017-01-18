package com.levup.simpleplayer.presenters;

import com.levup.simpleplayer.models.PlayListModel;
import com.levup.simpleplayer.models.Song;

import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by java on 18.01.2017.
 */

public class PlayListRepository {

    private Realm mRealm = Realm.getDefaultInstance();

    public Single<PlayListModel> loadPlayList() {
        return Single.create(singleSubscriber -> {
            mRealm.executeTransaction(realm -> {
                final PlayListModel result = realm
                        .where(PlayListModel.class)
                        .findFirst();
                singleSubscriber.onSuccess(result);
            });
        });
    }

    public void addSong(Song song) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PlayListModel playListModel = realm.where(PlayListModel.class).findFirst();
                playListModel.getSongRealmList().add(song);
            }
        });
    }

}
