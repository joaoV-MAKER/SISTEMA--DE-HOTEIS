import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
export interface Quarto {id:number;numero:string;tipo:string;capacidade:number;diaria:number;ativo:boolean}
export interface Hospede {id:number;nome:string;documento:string;email:string;telefone:string}
export interface Reserva {id:number;quarto:Quarto;hospede:Hospede;entrada:string;saida:string;diaria:number;valorTotal:number;status:string}
export function erro(e:HttpErrorResponse):string {return e.error?.message || e.error?.detail || (e.status===0?'Não foi possível conectar à API. Verifique se o Spring Boot está em execução.':'Não foi possível concluir a operação.');}
export const hoje=()=>{const d=new Date();return [d.getFullYear(),String(d.getMonth()+1).padStart(2,'0'),String(d.getDate()).padStart(2,'0')].join('-');};
@Injectable({providedIn:'root'}) export class Api {
 private http=inject(HttpClient);
 listar<T>(recurso:string){return this.http.get<T[]>('/api/'+recurso);}
 salvar<T>(recurso:string,dados:unknown,id?:number){return id?this.http.put<T>('/api/'+recurso+'/'+id,dados):this.http.post<T>('/api/'+recurso,dados);}
 excluir(recurso:string,id:number){return this.http.delete<void>('/api/'+recurso+'/'+id);}
 disponiveis(entrada:string,saida:string,reservaId?:number){return this.http.get<Quarto[]>('/api/quartos/disponiveis',{params:reservaId?{entrada,saida,reservaId}:{entrada,saida}});}
 acao(id:number,acao:string){return this.http.patch<Reserva>('/api/reservas/'+id+'/'+acao,{});}
}