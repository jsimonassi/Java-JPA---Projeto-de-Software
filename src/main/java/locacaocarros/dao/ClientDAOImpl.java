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
			// transiente - objeto novo: ainda n�o persistente
			// persistente - apos ser persistido 
			// destacado - objeto persistente n�o vinculado a um entity manager
		
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
				throw new ClientNotFoundException("Cliente n�o encontrado");
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
				throw new ClientNotFoundException("Cliente n�o encontrado");
			}

			// COMO PARA REMOVER UM PRODUTO NA JPA � PRECISO PRIMEIRAMENTE
			// RECUPER�-LO, QUANDO O  PRODUTO �  RECUPERADO SEU N�MERO  DE
			// VERS�O  J�  ATUALIZADO  VEM  JUNTO,  O  QUE  FAZ  COM QUE O 
			// CONTROLE DE VERS�O N�O FUNCIONE SE A REMO��O ACONTECER AP�S 
			// UMA ATUALIZA��O, OU SE OCORREREM DUAS REMO��ES EM  PARALELO 
			// DO MESMO PRODUTO.
			
			// LOGO, A  EXCE��O  OptimisticLockException NUNCA  ACONTECER�
			// NO CASO DE REMO��ES.

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
			
			// Caracter�sticas no m�todo find():
			// 1. � gen�rico: n�o requer um cast.
			// 2. Retorna null caso a linha n�o seja encontrada no banco.

			if(client == null) {
				throw new ClientNotFoundException("Cliente n�o encontrado");
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