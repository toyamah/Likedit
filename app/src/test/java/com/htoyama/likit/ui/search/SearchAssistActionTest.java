package com.htoyama.likit.ui.search;

import com.htoyama.likit.UserBuilder;
import com.htoyama.likit.domain.tag.Tag;
import com.htoyama.likit.domain.tag.TagBuilder;
import com.htoyama.likit.domain.tag.TagRepository;
import com.htoyama.likit.domain.user.User;
import com.htoyama.likit.domain.user.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

public class SearchAssistActionTest {
  @Mock UserRepository userRepository;
  @Mock TagRepository tagRepository;
  private SearchAssistAction action;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    action = new SearchAssistAction(userRepository, tagRepository);
  }

  @Test public void getAssist_emitAssist() {
    String query = "foo";
    List<Tag> tagList = tagList(2);
    when(userRepository.findByNameContaining(query))
        .thenReturn(Observable.just(Collections.emptyList()));
    when(tagRepository.findByNameContaining(query))
        .thenReturn(Observable.just(tagList));

    TestSubscriber<Assist> asssitSub = new TestSubscriber<>();
    action.getAssist(query).subscribe(asssitSub);
    asssitSub.awaitTerminalEvent();

    asssitSub.assertNoErrors();
    asssitSub.assertCompleted();
    Assist emitted = asssitSub.getOnNextEvents().get(0);
    assertThat(emitted.size()).isEqualTo(tagList.size() + 1); // 1 means tag header.
  }

  private List<Tag> tagList(int count) {
    List<Tag> tagList = new ArrayList<>(count);
    TagBuilder b = new TagBuilder();

    for (int i = 0; i < count; i++) {
      tagList.add(b.setId(i).build());
    }
    return tagList;
  }

  private List<User> userList(int count) {
    List<User> userList = new ArrayList<>(count);
    UserBuilder b = new UserBuilder();

    for (int i = 0; i < count; i++) {
      userList.add(b.setId(i).build());
    }
    return userList;
  }
}
