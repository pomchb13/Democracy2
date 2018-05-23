pragma solidity ^0.4.11;

contract Poll
{
	/// Definition of the struct Poll
	/// title: title of the Poll
	/// date_from: date when the Poll starts
	/// date_due: date when the Poll ends
	/// show_diagram: digram option fo the evaluation of votes
	struct PollData {
	    string title;
	    uint256 date_from;
	    uint256 date_due;
	    bool show_diagram;
	}
	
	/// Definition of the struct Answer
	/// answerNumber: number of the answer
	/// title: title of the Answer
	/// descriptionStr: a short description
	/// voteCount: the current count of votes the Answer got
	struct Answer {
		uint256 answerNumber;
	    string title;
	    string descriptionStr;
	    uint voteCount;
	}
	
	/// Definition of the struct Voter
	///	alreadyVoted: defines whether the Voter has already voted or not
	///	vote: defines which Candidate the Voter wants to vote for
	///	weight: defines if the Voter is allowed to vote
	struct Voter {
        bool alreadyVoted;
        uint256 vote;
        uint256 weight;
    }
	
	address admin;
	mapping(address => Voter) voters;
	PollData pollData;
	Answer[] answers;
	
	/// Creates a new Poll with numAnswers, p_title, p_date_from, p_date_due, p_show_diagram
	function Poll(uint numAnswers, string p_title, uint256 p_date_from, uint256 p_date_due, bool p_show_diagram) public {
		admin = msg.sender;
	    answers.length = numAnswers;
	    pollData.title = p_title;
	    pollData.date_from = p_date_from;
	    pollData.date_due = p_date_due;
	    pollData.show_diagram = p_show_diagram;
	}
	
	/// Function to stores the data of an Answer
	function storeAnswerData(uint answer, string p_title, string p_description) public {
		answers[answer].answerNumber = answer;
	    answers[answer].title = p_title;
	    answers[answer].descriptionStr = p_description;
	    answers[answer].voteCount = 0;
	}
	
	/// Function to give the Voter the right to vote and sets the contract address to the voter
	function giveRightToVote(address voter) public {
        if (msg.sender != admin || voters[voter].alreadyVoted) return;
        voters[voter].weight = 1;
    }
    
	/// Function to vote as a Voter for an Answer
    function vote(uint256 answer, address voter) public {
        if (voters[voter].alreadyVoted || answer >= answers.length) return;
        voters[voter].alreadyVoted = true;
        voters[voter].vote = answer;
        answers[answer].voteCount += 1;
    }
    
    
	/// Function which returns the data of a Poll
    function getPollData() constant public returns (string, uint256, uint256, bool) {
        return (pollData.title, pollData.date_from, pollData.date_due, pollData.show_diagram);
    }
    
	/// Function which returns the data of an Answer
    function getAnswerData(uint answer) constant public returns (string, string, uint)
    {
        return (answers[answer].title, answers[answer].descriptionStr, answers[answer].voteCount);
    }
	
	/// Function which returns the address of the admin
	function getAdminAddress() constant public returns (address)
	{
		return admin;
	}
	
	/// Function which returns a bool if the voter has already voted
	function getAlreadyVotedForVoter(address voter) constant public returns (bool)
	{
	    return voters[voter].alreadyVoted;
	}
	
	/// Function which returns the size of the answer array
	function getAnswerSize() constant public returns (uint256)
	{
		return answers.length;
	}
}