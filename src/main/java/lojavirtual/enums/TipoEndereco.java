package lojavirtual.enums;

public enum TipoEndereco {

	//1º:oq sera gravado no DB, 2º:Descrição(o que aparece no sistema para o user)
	//inviabiliza a pessoa de escrever errado...
	COBRANCA("Cobrança"),
	ENTREGA("Entrega");
	
	private String descricao;
	
	private TipoEndereco(String descricao) {
		this.descricao=descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
}
