	/**
	* Projeto 3: Carrinho de compras
    * Versão 1.0
    *@author Tarcisio Ribeiro/Thiago Sobral
    */
    
	create database dbcarrinho22;
    use dbcarrinho22;
    
    create table carrinho (
    codigo varchar(50) unique,
    produto varchar(100) not null,
    quantidade int not null,
    valor decimal (10,2)
    );
   
    describe carrinho;
    
    /* CRUD Create */
    
insert into carrinho 
(codigo, produto, quantidade, valor)
values
('1','Mouse',10,15.50);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('2','Monitor',5,350.00);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('3','Gabinete',2,890.50);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('4','Teclado',10,28.50);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('5','Tinta impressora',8,18.50);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('6','Cabo usb',18,12.50);

insert into carrinho 
(codigo, produto, quantidade, valor)
values
('7','Caneta',40,1.50);

update carrinho set valor=2.50 where codigo=6;

select * from carrinho;
