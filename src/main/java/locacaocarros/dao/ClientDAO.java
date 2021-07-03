package locacaocarros.dao;

import java.util.List;

import locacaocarros.exceptions.ClientNotFoundException;
import locacaocarros.models.Client;


public interface ClientDAO
{	
	long insert(Client client); 

	void update(Client client)
		throws ClientNotFoundException; 
	
	void delete(long id) 
		throws ClientNotFoundException; 
	
	Client findClient(long id) 
		throws ClientNotFoundException; 
	
	List<Client> getAllClients();
}