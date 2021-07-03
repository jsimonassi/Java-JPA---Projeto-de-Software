package locacaocarros;


import java.util.List;

import corejava.Console;
import locacaocarros.dao.ClientDAO;
import locacaocarros.exceptions.ClientNotFoundException;
import locacaocarros.exceptions.DeprecatedObjectException;
import locacaocarros.models.Client;

public class Main{
	public static void main (String[] args) {	
		
		String name;
		String cpf;
		String email;
		Client client;

		ClientDAO clientDAO = FabricaDeDAOs.getDAO(ClientDAO.class);

		while (true){	
			System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar um cliente");
			System.out.println("2. Alterar um cliente");
			System.out.println("3. Remover um cliente");
			System.out.println("4. Listar todos os clientes");
			System.out.println("5. Sair");
						
			int option = Console.readInt('\n' + 
							"Digite um n�mero entre 1 e 5:");
					
			switch (option)
			{	case 1:
				{
					name = Console.readLine('\n' + 
						"Informe o nome do cliente: ");
					cpf = Console.readLine(
						"Informe o cpf: ");
					email = Console.readLine(
						"Informe o email do cliente: ");
						
					client = new Client(name, cpf, email);
					
					long id = clientDAO.insert(client);
					
					System.out.println('\n' + "Cliente n�mero " + 
					    id + " inclu�do com sucesso!");	

					break;
				}

				case 2:
				{	int response = Console.readInt('\n' + 
						"Digite o n�mero do cliente que voc� deseja alterar: ");
										
					try
					{	client = clientDAO.findClient(response);
					}
					catch(ClientNotFoundException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + client.getId() + 
						"    Nome = " + client.getName() +
						"    cpf = " + client.getCpf() + 
				        "    Vers�o = " + client.getVersion());
												
					System.out.println('\n' + "O que voc� deseja alterar?");
					System.out.println('\n' + "1. Nome");
					System.out.println("2. Cpf");

					int updateOption = Console.readInt('\n' + 
											"Digite um n�mero de 1 a 2:");
					
					switch (updateOption)
					{	case 1:
							String newName = Console.
										readLine("Digite o novo nome: ");
							
							client.setName(newName);

							try
							{	clientDAO.update(client);

								System.out.println('\n' + 
									"Altera��o de nome efetuada com sucesso!");
							}
							catch(ClientNotFoundException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(DeprecatedObjectException e)
							{	System.out.println('\n' + "A opera��o n�o foi " +
							        "efetuada: os dados que voc� " +
							    	"tentou salvar foram modificados " +
							    	"por outro usu�rio.");
							}
								
							break;
					
						case 2:
							String newCpf = Console.
									readLine("Digite o novo cpf: ");
							
							client.setCpf(newCpf);

							try
							{	clientDAO.update(client);

								System.out.println('\n' + 
									"Altera��o de cpf efetuada com sucesso!");						
							}
							catch(ClientNotFoundException e)
							{	System.out.println('\n' + e.getMessage());
							}
							catch(DeprecatedObjectException e)
							{	System.out.println('\n' + "A opera��o n�o foi " +
							        "efetuada: os dados que voc� " +
							    	"tentou salvar foram modificados " +
							    	"por outro usu�rio.");
							}
								
							break;

						default:
							System.out.println('\n' + "Op��o inv�lida!");
					}

					break;
				}

				case 3:
				{	int id = Console.readInt('\n' + 
						"Digite o n�mero do cliente que voc� deseja remover: ");
									
					try
					{	client = clientDAO.findClient(id);
					}
					catch(ClientNotFoundException e)
					{	System.out.println('\n' + e.getMessage());
						break;
					}
										
					System.out.println('\n' + 
						"N�mero = " + client.getId() + 
						"    Nome = " + client.getName() +
					    "    Vers�o = " + client.getVersion());
														
					String resp = Console.readLine('\n' + 
						"Confirma a remo��o do cliente?");

					if(resp.equals("s"))
					{	try
						{	clientDAO.delete(client.getId());
							System.out.println('\n' + 
								"Cliente removido com sucesso!");
						}
						catch(ClientNotFoundException e)
						{	System.out.println('\n' + e.getMessage());
						}
					}
					else
					{	System.out.println('\n' + 
							"Produto n�o removido.");
					}
					
					break;
				}

				case 4:
				{	
					List<Client> clients = clientDAO.getAllClients();

					for (Client clientObj : clients)
					{	
						System.out.println('\n' + 
							"Id = " + clientObj.getId() +
							"  Nome = " + clientObj.getName() +
							"  cpf = " + clientObj.getCpf() +
							"  Data Cadastro = " + clientObj.getRegisteredAt() +
							"  Vers�o = " + clientObj.getVersion());
					}
					
					break;
				}

				case 5:
				{	System.exit(0);
					break;
				}

				default:
					System.out.println('\n' + "Op��o inv�lida!");
			}
		}		
	}
}
