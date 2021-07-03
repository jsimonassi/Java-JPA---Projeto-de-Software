package locacaocarros.models;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Transient;
import javax.persistence.Version;
//import locacaocarros.Util;

@Entity
@Table(name="clients")
public class Client
{	
	private Long id;
	private String name;
	private String cpf;
	private String rg;
	private String address;
	private String emailAddress;
	private Timestamp registeredAt;
	private int version;
	

	public Client()
	{
	}

	public Client(String name, 
	               String cpf, 
	               String emailAddress)
	{	this.name = name;
		this.cpf = cpf;
		this.emailAddress = emailAddress;	
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId()
	{	return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="cpf")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name="rg")
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name="address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="email")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name="registered_at")
	public Timestamp getRegisteredAt() {
		return registeredAt;
	}

	
	public void setRegisteredAt(Timestamp registeredAt) {
		this.registeredAt = registeredAt;
	}

	@Version
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}

