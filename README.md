API de Transações – Desafio Dev Itaú
Autor: Mateus

Tecnologia: Java + Spring Boot
Armazenamento: Em memória (sem banco de dados)
1. Introdução
Esta API REST foi desenvolvida como parte do Desafio Técnico Dev Itaú. A aplicação recebe transações financeiras, armazena em memória e calcula estatísticas das transações ocorridas nos últimos 60 segundos.

2. Arquitetura
A aplicação segue arquitetura em camadas:
Controller → Service → Repository → Armazenamento em memória
DTO / Model
Global Exception Handler

3. Como Executar
Pré-requisitos: Java 17+ e Maven.
Comando: mvn spring-boot:run
URL base: http://localhost:8080

4. Endpoints
POST /transacao
DELETE /transacao
GET /estatistica
GET /health

5. Regras de Negócio
• valor deve ser maior ou igual a 0
• dataHora deve estar no passado ou presente
• dataHora não pode estar no futuro
• Estatísticas consideram últimos 60 segundos
• Quando não houver transações → todos valores retornam 0
