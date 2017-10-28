package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 * Created by yeshua on 10/28/2017.
 */
public class UserTest {
    Board board;
    User author;
    User user2;
    Question question;
    Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        board = new Board("Programming");
        author = new User(board, "Yeshua");
        user2 = new User(board, "Haleigh");
        question = new Question(author, "What color is that?");
        answer = new Answer(question, user2, "idk");
        board.addQuestion(question);
        board.addAnswer(answer);

    }
    @Test
    public void upvotedQuestionIncreasesQuestionersReputation() throws Exception {
        question.addUpVoter(user2);

        assertEquals("question",5, author.getReputation());
    }

    @Test
    public void upvotedAnswerIncreasesAnswerersReputation() throws Exception {
        answer.addUpVoter(author);

        assertEquals("answer",10, user2.getReputation());
    }

    @Test
    public void acceptedAnswerIncreasesAnswerer(){
        author.acceptAnswer(answer);

        assertEquals(15, user2.getReputation());
    }

    @Test(expected = VotingException.class)
    public void questionVotingForSelfThrowsException(){
        author.upVote(question);

    }

    @Test(expected = VotingException.class)
    public void answerVotingForSelfThrowsException(){
        user2.upVote(answer);
    }

    @Test(expected = AnswerAcceptanceException.class)
    public void acceptingAnAswerOnlyAllowedByOriginalQuestioner(){
        user2.acceptAnswer(answer);
    }

    @Test
    public void acceptingAnswerByUnoriginalQuestionerNotAllowed(){
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only Yeshua can accept this answer as it is their question");

        user2.acceptAnswer(answer);

    }




}