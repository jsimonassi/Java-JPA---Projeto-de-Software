package locacaocarros.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import locacaocarros.FabricaDeEntityManager;
import locacaocarros.exceptions.ClientNotFoundException;
import locacaocarros.exceptions.DeprecatedObjectException;
import locacaocarros.models.Client;

public class ClientDAOImpl implements ClientDAO{
	
	public long insert(Client client) {
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try{
			// transiente - objeto novo: ainda não persistente
			// persistente - apos ser persistido 
			// destacado - objeto persistente não vinculado a um entity manager
		
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
			
			em.persist(client);
			
			tx.commit();
			return client.getId();
		} 
		catch(RuntimeException e){
			
			if (tx != null){
				try{
					tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally{
			em.close();
		}
	}

	public void update(Client client) throws ClientNotFoundException {
		EntityManager em = null;
		EntityTransaction tx = null;
		Client clientAux = null;
		try {
			
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();
			
			clientAux = em.find(Client.class, client.getId(), LockModeType.PESSIMISTIC_WRITE);
			
			if(clientAux == null) {
				tx.rollback();
				throw new ClientNotFoundException("Cliente não encontrado");
			}
			em.merge(client);
			tx.commit();
		} 
		catch(OptimisticLockException e) {
			if (tx != null) {
		       tx.rollback();
		    }
			throw new DeprecatedObjectException();
		}
		catch(RuntimeException e) {
		   
			if (tx != null) {
		       try {
		           tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally {
		   em.close();
		}
	}

	public void delete(long id) throws ClientNotFoundException {
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			
			em = FabricaDeEntityManager.criarSessao();
			tx = em.getTransaction();
			tx.begin();

			Client produto = em.find(Client.class, id, LockModeType.PESSIMISTIC_WRITE);
			
			if(produto == null) {
				tx.rollback();
				throw new ClientNotFoundException("Cliente não encontrado");
			}

			// COMO PARA REMOVER UM PRODUTO NA JPA É PRECISO PRIMEIRAMENTE
			// RECUPERÁ-LO, QUANDO O  PRODUTO É  RECUPERADO SEU NÚMERO  DE
			// VERSÃO  JÁ  ATUALIZADO  VEM  JUNTO,  O  QUE  FAZ  COM QUE O 
			// CONTROLE DE VERSÃO NÃO FUNCIONE SE A REMOÇÃO ACONTECER APÓS 
			// UMA ATUALIZAÇÃO, OU SE OCORREREM DUAS REMOÇÕES EM  PARALELO 
			// DO MESMO PRODUTO.
			
			// LOGO, A  EXCEÇÃO  OptimisticLockException NUNCA  ACONTECERÁ
			// NO CASO DE REMOÇÕES.

			em.remove(produto);

			tx.commit();
		} 
		catch(RuntimeException e) {  
		 
			if (tx != null)  {
		      try {
		        	tx.rollback();
		        }
		        catch(RuntimeException he)
		        { }
		    }
		    throw e;
		}
		finally { 
		  em.close();
		}
	}

	public Client findClient(long id) throws ClientNotFoundException {
		EntityManager em = null;
		
		try {
			em = FabricaDeEntityManager.criarSessao();

			Client client = em.find(Client.class, id);
			
			// Características no método find():
			// 1. É genérico: não requer um cast.
			// 2. Retorna null caso a linha não seja encontrada no banco.

			if(client == null) {
				throw new ClientNotFoundException("Cliente não encontrado");
			}
			return client;
		} 
		finally { 
		  em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Client> getAllClients() {
		EntityManager em = null;
		
		try {
			em = FabricaDeEntityManager.criarSessao();

			List<Client> clients = em
				.createQuery("select c from Client c order by c.id")
				.getResultList();

			// Retorna um List vazio caso a tabela correspondente esteja vazia.
			
			return clients;
		} 
		finally {
		   em.close();
		}
	}
}