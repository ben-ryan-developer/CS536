// The following methods unparse  AST nodes used in CSX

public class Unparsing extends Visitor {
	
	//have to only print line number, tab, and word print once for a printlist, similar to 
	//readlist
	private int countFlag = 0;
	
	static void genIndent(int indent){
		for (int i=1;i<=indent;i++)
		    System.out.print("   ");
	}

	static void printOp(int op) {
		switch (op) {
			case sym.PLUS:
				System.out.print(" + ");
				break;
			case sym.MINUS:
				System.out.print(" - ");
				break;
			case sym.EQ:
				System.out.print(" == ");
				break;
			case sym.NOTEQ:
				System.out.print(" != ");
				break;
			case sym.COR:
				System.out.print(" || ");
				break;
			case sym.CAND:
				System.out.print(" && ");
				break;
			case sym.GT:
				System.out.print(" > ");
				break;
			case sym.LT:
				System.out.print(" < ");
				break;
			case sym.GEQ:
				System.out.print(" >= ");
				break;
			case sym.LEQ:
				System.out.print(" <= ");
				break;
			case sym.TIMES:
				System.out.print(" * ");
				break;
			case sym.SLASH:
				System.out.print(" / ");
				break;
			default:
				throw new Error();
		}
		return;
	}

	void visit(csxLiteNode n,int indent){
		System.out.println(n.linenum + ":\t" + " {");
		this.visit(n.progDecls,1);
		this.visit(n.progStmts,1);
		System.out.println(n.linenum + ":\t" + " } EOF");
	}
	
	void visit(fieldDeclsNode n,int indent){
		this.visit(n.thisField,indent);
		this.visit(n.moreFields,indent);
	}
	
	void visit(nullFieldDeclsNode n,int indent){}

	void visit(stmtsNode n,int indent){
		this.visit(n.thisStmt,indent);
		this.visit(n.moreStmts,indent);
	}
	
	void visit(nullStmtsNode n,int indent){}

	void visit(varDeclNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
	    this.visit(n.varType,0);
		System.out.print(" ");
		this.visit(n.varName,0);
		if(!n.initValue.isNull()){
			System.out.print(" = ");
			this.visit(n.initValue, 0);
		}
		System.out.println(";");
	}
	
	void visit(nullTypeNode n,int ident){}
	
	void visit(intTypeNode n,int ident){
		System.out.print("int");
	}
	
	void visit(boolTypeNode n,int ident){
		System.out.print("bool");
	}
	
	void visit(identNode n,int indent){
		System.out.print(n.idname);
	}

	void visit(nameNode n,int indent){
		System.out.print(n.varName.idname);
		if(!n.subscriptVal.isNull()){
			System.out.print("[");
			this.visit(n.subscriptVal, 0);
			System.out.print("]");
		}
	}
	
	void visit(asgNode n,int indent){
		System.out.print(n.linenum + ":\t"); 
		genIndent(indent);
		this.visit(n.target,0);
		System.out.print(" = ");
		this.visit(n.source,0);
		System.out.println(";");
	}
	
	void visit(incrementNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.target, 0);
		System.out.println("++;");
	}
		  
	void visit(decrementNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.target, 0);
		System.out.println("--;");
	}
	
	void visit(ifThenNode n,int indent){
		//checks for the first instance of this visit call
		if(countFlag == 0){
			System.out.print(n.linenum + ":\t");
			genIndent(indent);
			System.out.print("if (");
		}
		countFlag++;
		this.visit(n.condition,0);
		//decrement after each recursive call returns till the last call will decrement countFlag
		//back to 0 so we print our closing parenthesis
		countFlag--;
		if(countFlag == 0){
			System.out.println(")");
		}
		this.visit(n.thenPart,indent+1);
		if(!n.elsePart.isNull()){
			System.out.print(n.linenum + ":\t");
			genIndent(indent);
			System.out.println("else");
			this.visit(n.elsePart, indent + 1);
		}
	}
	  
	void visit(blockNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("{");
		this.visit(n.decls,indent+1);
		this.visit(n.stmts,indent+1);
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("}");
	}

	
	void visit(binaryOpNode n,int indent){
		System.out.print("(");
		this.visit(n.leftOperand,0);
		printOp(n.operatorCode);
		this.visit(n.rightOperand,0);
		System.out.print(")");
	}
	
	void visit(intLitNode n,int indent){
		if (n.intval>=0)
			System.out.print(n.intval);
		else	System.out.print("~"+-n.intval);
	}
	 
	void visit(classNode n,int indent){
		System.out.print(n.linenum + ": ");
		genIndent(indent);
		System.out.println("class " + n.className.idname + " {");
		this.visit(n.members, indent + 1);
		System.out.print(n.linenum + ": ");
		genIndent(indent);
		System.out.println("} EOF");
	}

	void  visit(memberDeclsNode n,int indent){
		this.visit(n.fields, indent);
		this.visit(n.methods, indent);
	}
	 
	void  visit(methodDeclsNode n,int indent){
		this.visit(n.thisDecl, indent);
		this.visit(n.moreDecls, indent);
	}
	 
	void visit(nullStmtNode n,int indent){}
	 
	void visit(nullReadNode n,int indent){}

	void visit(nullPrintNode n,int indent){}

	void visit(nullExprNode n,int indent){}

	void visit(nullMethodDeclsNode n,int indent){}

	void visit(methodDeclNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.returnType, indent + 1);
		System.out.print(" " + n.name.idname + " (");
		if(!n.args.isNull()){
			this.visit(n.args, 0);
		}
		System.out.println(") {");
		this.visit(n.decls, indent + 1);
		this.visit(n.stmts, indent + 1);
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("}");
	}

	 
	void visit(argDeclsNode n,int indent){
		this.visit(n.thisDecl, indent);
		if(!n.moreDecls.isNull()){
			System.out.print(", ");
			this.visit(n.moreDecls, indent);
		}
	}

	void visit(nullArgDeclsNode n,int indent){}
	
	void visit(valArgDeclNode n,int indent){
		genIndent(indent);
		this.visit(n.argType, indent);
		System.out.print(" " + n.argName.idname);
	}
	
	void visit(arrayArgDeclNode n,int indent){
		genIndent(indent);
		this.visit(n.elementType, indent);
		System.out.print(" " + n.argName.idname + " [ ]");
	}
	
	void visit(constDeclNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.print("const " + n.constName.idname);
		if(!n.constValue.isNull()){
			System.out.print(" = ");
			this.visit(n.constValue, 0);
		}
		System.out.println(";");
	}
	 
	void visit(arrayDeclNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		this.visit(n.elementType, 0);
		System.out.print(" " + n.arrayName.idname + " [ ");
			if(!n.arraySize.isNull()){
				this.visit(n.arraySize, 0);
			}
		System.out.println(" ];");
	}
	
	void visit(charTypeNode n,int ident){
		System.out.print("char");
	}
	
	void visit(voidTypeNode n,int ident){
		System.out.print("void");
	}

	void visit(whileNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		if(!n.label.isNull()){
			this.visit(n.label, 0);
			System.out.print(": ");
		}
		System.out.print("while (");
		this.visit(n.condition, 0);
		System.out.println(")");
		this.visit(n.loopBody, indent + 1);
	}

	void visit(breakNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("break " + n.label.idname + ";");
	}
	
	void visit(continueNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.println("continue " + n.label.idname + ";");
	}
	  
	void visit(callNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.print(n.methodName.idname + "(");
		if(!n.args.isNull()){
			this.visit(n.args, 0);
		}
		System.out.println(");");
	}

	void visit(printNode n,int indent){
		//checks for the first instance of visit
		if(countFlag == 0){
			System.out.print(n.linenum + ":\t");
			genIndent(indent);
			System.out.print("print(");
		}
		countFlag++;
	    this.visit(n.outputValue, 0);
	    if(!n.morePrints.isNull()){
	    	System.out.print(", ");
	    	this.visit(n.morePrints, 0);
	    }
	    //checks for the last countFlag after each recursive call returns and decrements countFlag 
	    if(countFlag == 1){
	    	System.out.println(");");
	    }
	    countFlag--;
	}
	  
	void visit(readNode n,int indent){
		//checks for the first instance of visit
		if (countFlag == 0){
			System.out.print(n.linenum + ":\t");
			genIndent(indent);
			System.out.print("read(");
		}
		countFlag++;
		this.visit(n.targetVar, 0);
		if(!n.moreReads.isNull()){
			System.out.print(", ");
		    this.visit(n.moreReads, 0);
		}
		//checks for the last countFlag after each recursive call returns and decrements countFlag 
		if(countFlag == 1){
			System.out.println(");");
		}
		countFlag--;
	}
	  
	void visit(returnNode n,int indent){
		System.out.print(n.linenum + ":\t");
		genIndent(indent);
		System.out.print("return");
		if(!n.returnVal.isNull()){
			System.out.print(" ");
			this.visit(n.returnVal, 0);
		}
		System.out.println(";");
	}

	void visit(argsNode n,int indent){
		this.visit(n.argVal, indent);
		if(!n.moreArgs.isNull()){
			System.out.print(", ");
			this.visit(n.moreArgs, indent);
		}
	}
	  	
	void visit(nullArgsNode n,int indent){}
		
	void visit(castNode n,int indent){
		System.out.print("((");
		this.visit(n.resultType, 0);
		System.out.print(") ");
		this.visit(n.operand, 0);
		System.out.print(")");
	}

	void visit(fctCallNode n,int indent){
		System.out.print(n.methodName.idname + "(");
		if(!n.methodArgs.isNull()){
			this.visit(n.methodArgs, 0);
		}
		System.out.print(")");
	}

	void visit(unaryOpNode n,int indent){
		System.out.print("(! ");
		this.visit(n.operand, 0);
		System.out.print(")");
	}

	void visit(charLitNode n,int indent){
		if(n.charval == '\n'){
			System.out.print("'\\n'");
			return;
		}
		else if (n.charval == '\t'){
			System.out.print("'\\t'");
			return;
		}
		else if (n.charval == '\r'){
			System.out.print("'\\r'");
			return;
		}
		else if (n.charval == '\\'){
			System.out.print("'\\\\'");
			return;
		}
		else if (n.charval == '\''){
			System.out.print("'\\''");
			return;
		}
		System.out.print("'" + n.charval + "'");
	}
	  
	void visit(strLitNode n,int indent){
		System.out.print(n.strval);
	}

	void visit(trueNode n,int indent){
		System.out.print("true");
	}

	void visit(falseNode n,int indent){
		System.out.print("false");
	}
}
