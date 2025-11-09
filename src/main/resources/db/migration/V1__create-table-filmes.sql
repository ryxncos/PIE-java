-- Migration: V1__Create_filmes_table.sql
-- Description: Cria a tabela de filmes com os campos especificados

CREATE TABLE filmes (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    diretor VARCHAR(255) NOT NULL,
    genero VARCHAR(100),
    ano_lancamento VARCHAR(4),
    sinopse TEXT,
    elenco TEXT,
    review TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para melhor performance
CREATE INDEX idx_filmes_titulo ON filmes(titulo);
CREATE INDEX idx_filmes_diretor ON filmes(diretor);
CREATE INDEX idx_filmes_genero ON filmes(genero);
CREATE INDEX idx_filmes_ano_lancamento ON filmes(ano_lancamento);

-- Comentários sobre a tabela e colunas
COMMENT ON TABLE filmes IS 'Tabela para armazenar informações sobre filmes';
COMMENT ON COLUMN filmes.id IS 'ID único do filme (chave primária)';
COMMENT ON COLUMN filmes.titulo IS 'Título do filme';
COMMENT ON COLUMN filmes.diretor IS 'Nome do diretor do filme';
COMMENT ON COLUMN filmes.genero IS 'Gênero do filme';
COMMENT ON COLUMN filmes.ano_lancamento IS 'Ano de lançamento do filme';
COMMENT ON COLUMN filmes.sinopse IS 'Sinopse/resumo do filme';
COMMENT ON COLUMN filmes.elenco IS 'Lista de elenco principal';
COMMENT ON COLUMN filmes.review IS 'Review/crítica do filme';
COMMENT ON COLUMN filmes.data_criacao IS 'Data de criação do registro';
COMMENT ON COLUMN filmes.data_atualizacao IS 'Data da última atualização do registro';