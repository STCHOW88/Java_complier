package cop5556fa18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import cop5556fa18.PLPAST.Declaration;

public class symbolTable {
	int current_scope;
	int next_scope;
	Stack<Integer> scope_stack;
	HashMap<String, List<thing>> map;
	
	public symbolTable() {
		scope_stack = new Stack<>();
		map = new HashMap<>();
		this.scope_stack.push(0);
		current_scope = 0;
		next_scope = 1;
	}
	
	public class thing{
		int current_scope_thing;
		Declaration dec_thing = null;
		
		public thing(int current_scope, Declaration dec) {
			this.current_scope_thing = current_scope;
			this.dec_thing = dec; 
		}
	}
	
	
	public void enterScope(){   
		current_scope = next_scope++; 
		scope_stack.push(current_scope);
	}

	public void closeScope(){  
		scope_stack.pop();
		if(!scope_stack.isEmpty()) current_scope = scope_stack.peek();
		else current_scope = 0;
	}
	
	List<thing> list = new ArrayList<>();
	

	public boolean insert(String ident, Declaration dec) {
		List<thing> temp = new ArrayList<>();
		thing t = new thing(current_scope, dec);
		if(map.containsKey(ident)) {
			temp = map.get(ident);
			for(thing e : temp) {
				if(e.current_scope_thing==current_scope) {
					return false; //we already have the scope number, dont need to put a new one
				}
			}
		}
		temp.add(t);
		map.put(ident, temp);
		return true;
		
	}
	
	
	public Declaration lookup(String ident) {
		if(!map.containsKey(ident)) {
			return null;
		}
		else {
			List<Integer> list = new ArrayList<>();
			for(thing e : map.get(ident)) {
				if(e.current_scope_thing<=current_scope) {
					list.add(e.current_scope_thing);
				}
			}
			if(list.size()==0) return null;
			int max = 0;
			for(int can : list){
				if(can > max){
					max = can;
				}
			}

			
			for(thing e : map.get(ident)) {
				if(e.current_scope_thing==max) {
					return e.dec_thing;
				}
			}
		}
		return null;
	}
}



