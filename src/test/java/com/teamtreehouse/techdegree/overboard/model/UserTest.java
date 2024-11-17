package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {

    private Board board;
    private User user1;
    private User user2;
    private User user3;
    private Question question1;
    private Question question2;
    private Answer answer1;
    private Answer answer2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        user1 = new User(board, "testUser1");
        user2 = new User(board, "testUser2");
        user3 = new User(board, "testUser3");
        question1 = user1.askQuestion("How do you declare a variable?");
        answer1 = user2.answerQuestion(question1,"type variableName = value;");
        question2 = user2.askQuestion("What are the primitive types?");
        answer2 = user1.answerQuestion(question2,"Primitive data types - includes byte , short , int , long , float , double , boolean and char.");
    }

    @Test
    public void usersReputationIncreasesBy5AfterUpVote() throws Exception {
        int prevReputation = user1.getReputation();
        user3.upVote(question1);

        assertEquals(0, prevReputation);
        assertEquals(5, user1.getReputation());
    }

    @Test
    public void answerersReputationIncreasesBy10AfterUpVote() throws Exception {
        int prevReputation = user2.getReputation();
        user3.upVote(answer1);

        assertEquals(0, prevReputation);
        assertEquals(10, user2.getReputation());
    }

    @Test
    public void answerersReputationIncreasesBy15AfterAnswerAccepted() throws Exception {
        int prevReputation = user2.getReputation();
        user1.acceptAnswer(answer1);

        assertEquals(0, prevReputation);
        assertEquals(15, user2.getReputation());
    }

    @Test
    public void answerAcceptedSuccessfullyByAuthor() throws Exception {
        user1.acceptAnswer(answer1);

        assertEquals(true, answer1.isAccepted());
    }

    @Test
    public void userCannotUpVoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user1.upVote(question1);
    }

    @Test
    public void userCannotDownVoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user1.downVote(question1);
    }

    @Test
    public void userCannotUpVoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user2.upVote(answer1);
    }

    @Test
    public void userCannotDownVoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        user2.downVote(answer1);
    }

    @Test
    public void onlyAuthorsCanAcceptAnswer() throws Exception {
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only testUser1 can accept this answer as it is their question");

        user3.acceptAnswer(answer1);
    }
}