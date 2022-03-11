package hw4.karena;
/*Karena Qian
 * 8.9.2021
 * CS 211 Section C
 * Instructor: Neelakantan Kartha
 * Grammar Solver */
/* This is the class GrammarSolver that performs a few functions on a list of BNF grammars,
 * including storing and sorting each grammar, checking if a given symbol is a non-terminal
 * symbol, generating a certain number of random expressions using a certain grammar, and
 * giving a bracketed list of the non-terminal symbols.*/
import java.util.*;
public class GrammarSolver {
	
	//for storing grammars (keys: non-terminal symbols, values: list of rules)
	private SortedMap<String, List<String>> grammerList = new TreeMap<>();	
	
	//stores BNF grammars into a sorted Map
	public GrammarSolver(List<String> grammar){
		List<String> keys = new ArrayList<>();
		for(String s : grammar) {
			String[] nonTerminal = s.split(":");
			if(keys.contains(nonTerminal[0])) {
				throw new IllegalArgumentException();
			}
			else {
				keys.add(nonTerminal[0]);
				String[] rules = nonTerminal[1].split("[|]");
				List<String> newList = new ArrayList<>();
				for(int i = 0; i < rules.length; i++) {
					newList.add(rules[i].trim());
				}
				grammerList.put(nonTerminal[0], newList);
			}
		}
	}
	
	//checks whether a given symbol is a non-terminal symbol
	public boolean grammarContains(String symbol) {
		if(grammerList.keySet().contains(symbol)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//generates a certain number of random expressions from a certain grammar
	//(indicated by a given non-terminal symbol)
	public String[] generate(String symbol, int times) {
		String[] s = new String[times];
		for(int i = 0; i < times; i++) {
			s[i] = generateString(symbol);
		}
		return s;
	}
	
	//generate helper method
	private String generateString(String symbol) {
		String s = "";
		// find rules for non-terminal symbol, throw exception if no rule is found
		List<String> rules = grammerList.get(symbol);
		if(rules.isEmpty()) {
			throw new IllegalArgumentException();
		}
		else {
			// randomly select one of the rules to apply
			Random random = new Random();
			String terminal = rules.get(random.nextInt(rules.size() - 0));
			String[] terminalParts = terminal.split("[ \t]+");
			// append the terminal tokens in random rule while calling generateString recursively for 
			// the non-terminal tokens
			for(int j = 0; j < terminalParts.length; j++) {
				if(grammerList.keySet().contains(terminalParts[j])) {
					s += generateString(terminalParts[j]); //recursive case
				}
				else {
					s += terminalParts[j].trim() + " "; //base case
				}
			}
		}
		return s;
	}

	//gives a sorted comma-separated list of non-terminal symbols in grammar
	public String getSymbols() {
		List<String> keys = new ArrayList<>(grammerList.keySet());
		String s = "[";
		for(int i = 0; i < keys.size() - 1; i++) {
			s += keys.get(i) + ", ";
		}
		s += keys.get(keys.size() - 1) + "]";
		return s;
	}
}
