# Projeto SD

## Notações do cliente

* ```registar <nome> <pass>``` para registar um novo cliente
* ```login <nome> <pass>``` para dar login (só testa ainda)
* ```upload <titulo> <interprete> <ano> <etiqueta1,etiqueta2,...> <path>``` para dar upload (as etiquetas têm de ser separadas por vírgulas)
* ```download <id> <diretoria>``` para fazer download
* ```show users``` para mostrar todos os users registados (apagar depois)
* ```show musicas``` para mostrar todos as músicas registadas (apagar depois)

## Problemas

1. Socket fecha após execução do método saveFile no ServerWorker (devido aos closes)
    - Resolução: criar 2 sockets ou não usar DataInputStream e FileOutputStream. No entanto acho que assim não é possível criar o file na diretoria destino para formar o mp3 da música. 
