pragma solidity ^0.4.11;

contract Election
{
	/// Definition of the struct Voter
	///	alreadyVoted: defines whether the Voter has already voted or not
	///	vote: defines which Candidate the Voter wants to vote for
	///	weight: defines if the Voter is allowed to vote
    struct Voter {
        bool alreadyVoted;
        uint256 vote;
        uint256 weight;
    }
	
	/// Definition of the struct Candidate
	/// candidateNumber: number of the candidate
	/// title: academic title of the Candidate
	/// firstname: firstname of the Candidate
	/// lastname: lastname of the Candidate
	/// birthday: birthday of the Candidate
	/// party: party of the Candidate
	/// slogan: slogan of the Candidate
	/// voteCount: the current count of votes the Candidate got
	/// imagePath: path to the image of the candidate
    struct Candidate {
        uint256 candidateNumber;
        string title;
        string firstname;
        string lastname;
        uint256 birthday;
        string party;
        string slogan;
        uint voteCount;
		string imagePath;
    }
	
	/// Definition of the struct ElectionData
	/// title: title of the Election
	/// date_from: date when the Election starts
	/// date_due: date when the Election ends
	/// show_diagram: digram option for the evaluation of votes
    struct ElectionData
    {
        string title;
        uint256 date_from;
        uint256 date_due;
        bool show_diagram;
    }

    address admin;
    mapping(address => Voter) voters;
    Candidate[] candidates;
    ElectionData electionData;
 
	/// Creates a new Election with numCandidates, p_title, p_date_from, p_date_due, p_show_diagram
    function Election(uint numCandidates, string p_title, uint256 p_date_from, uint256 p_date_due, bool p_show_diagram) public 
	{
        admin = msg.sender;
        candidates.length = numCandidates;
        electionData.title = p_title;
        electionData.date_from = p_date_from;
        electionData.date_due = p_date_due;
        electionData.show_diagram = p_show_diagram;
    }
    
	// Function to stores the data of a Candidate
    function storeCandidateData(uint candidate, string p_title, string p_firstname, string p_lastname, uint256 p_birthday, string p_party, string p_slogan, string p_imagePath) public
    {
		candidates[candidate].candidateNumber = candidate;
        candidates[candidate].title = p_title;
        candidates[candidate].firstname = p_firstname;
        candidates[candidate].lastname = p_lastname;
        candidates[candidate].birthday = p_birthday;
        candidates[candidate].party = p_party;
        candidates[candidate].slogan = p_slogan;
        candidates[candidate].voteCount = 0;
		candidates[candidate].imagePath = p_imagePath;
    }

    /// Function to give the Voter the right to vote and sets the contract address to the voter
    function giveRightToVote(address voter) public 
	{
        if (msg.sender != admin || voters[voter].alreadyVoted) return;
        voters[voter].weight = 1;
    }

    /// Function to vote as a Voter for a Candidate
    function vote(uint256 candidate, address voter) public 
	{
        if (voters[voter].alreadyVoted || candidate >= candidates.length) return;
        voters[voter].alreadyVoted = true;
        voters[voter].vote = candidate;
        candidates[candidate].voteCount += 1;
    }
	
	/// Function which returns the data of an Election
	function getElectionData() constant public returns (string, uint256, uint256, bool)
	{
		return (electionData.title, electionData.date_from, electionData.date_due, electionData.show_diagram);
	}
    
	/// Function which returns the data of a Candidate
	function getCandidate(uint candidate) constant public returns (string, string, string, uint256, string, string, uint)
	{
		return (candidates[candidate].title, candidates[candidate].firstname, candidates[candidate].lastname, 
				candidates[candidate].birthday, candidates[candidate].party, candidates[candidate].slogan, 
				candidates[candidate].voteCount);
	}
	
	/// function which returns the image path of the candidate image
	function getCandidateImagePath(uint candidate) constant public returns(string)
	{
	    return candidates[candidate].imagePath;
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
	
	/// Function which returns the size of the candidate array
	function getCandidateSize() constant public returns (uint256)
	{
		return candidates.length;
	}
}