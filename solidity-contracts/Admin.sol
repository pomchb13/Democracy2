pragma solidity ^0.4.11;

contract Admin
{
    address[] admins;
	address[] contractArray;
    uint256 adminCount;
	uint256 contractCount;
	mapping(address => address) userMapping;
    
	/// Sets the default parameters for the contract
    function Admin() public
    {
        admins.length = 1;
        adminCount = 1;
		contractArray.length = 200;
		contractCount = 0;
        admins[0] = msg.sender;
    }
    
	/// Function to add an admin address to the array
    function addAdmin(address admin) public
    {
        adminCount++;
        admins.length = adminCount;
        admins[adminCount - 1] = admin;  
    }
    
	/// Function to get the admin with a specific index
    function getAdmin(uint i) constant public returns(address)
    {
		return admins[i];
    }
	
	/// Function which checks if the given address is an admin
	function checkIfAdmin(address admin) constant public returns(bool)
	{
		for(uint i; i < admins.length; i++)
		{
			if(admins[i] == admin)
			{
				return true;
			}
		}
		return false;
	}
	
	/// Function to add a contract address to the array
	function addContractAddress(address contract) public
	{
		contractArray[contractCount] = contract;
		contractCount++;
	}
	
	/// Function to get the contract address with a specific index
	function getContractAddress(uint i) constant public returns(address)
	{
		return contractArray[i];
	}
	
	/// Function to get the total nubmer of admins
	function getAdminCount() constant public returns (uint256)
	{
		return adminCount;
	}
	
	/// Function to get the total number of contracts
	function getContractCount() constant public returns (uint256)
	{
		return contractCount;
	}
	
	/// Function responsible for mapping the voter to a contract
	function addContractAddressToVoter(address contract, address voter) public
	{
		userMapping[voter] = contract;
	}
	
	/// Function which returns the address of the contract where the voter is allowed to vote
	function getContractAddressForVoter(address voter) constant public returns(address)
	{
	    return userMapping[voter];
	}
}