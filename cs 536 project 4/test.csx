class test {
## sample test prog for proj #3 (parser) --
   may not type-check or execute correctly ##

int i = 0;
int I = 99; ## duplicate declaration##
int j = ~123;
int bigPos = 11111111;
int bigNeg = ~2222222;
int ii = '9';   ## Initializer type error ##
bool ja = true;
bool nein = false;
char c = 'c';
char nl = '\n';
char tab = '\t';
char bslash = '\\';
char tic = '\'';
CONST ten = 10;
const Ten = "ten";    ## duplicate declaration ##
int ar[100];
int ar2[~100];     ## can't have negative array index ##
int ar3[10];
int ar4[10];
char ar5[4];

void jj(){int i; return;}

void test() {
  ten=9;      ## assignment to constant variable ##
  j = 222;
  ii = 19;
  j = 'x';    ## wrong type assignment ##
  j = "x";    ## wront type assignment ##
  j = test;   ## assign method to int ##
  ar=ar3;     ## different array size assignment ##
  ar3=ar4;
  ar5=ar4;    ## wrong type assignment ##
  ar5 = "abcd";
  ar5 = "a\\cd";
  ar5 = "ad";    ## string lit has different size from char array ##
  ar5[2] = ar5[3];
  j[2] = ar5[3];   ## j is not an array ##
  ar5[2] = ar5[true];   ## can't use bool as subscript ##
  
}

void IOtest() {
   int i; char c; int ar[10];
   char cc[80];
   const t=10;
   read(i,c);
   read(t,IOtest,ar); ## can't read IOtest, ar ##
   print(10,false,'c',"abcd",cc);
   print(Iotest,ar);  ## can't print Iotest, ar ##
}

int f() {
	return 10;
	return;  ## can't return without expression ##
	return false;  ## wrong type return ##
}

void f() {  ## duplicate declaration ##
	return 10;  ## can't return with expression ##
	return;
	return false;  ## can't return with expression ##
}

void pp(int i, char c[], bool b) {
	char cc[100];
	int ii[100];
	udef();  ## undeclared variable ##
	c();   ## attempt to call non-method ##
	pp(1,cc,false);  
	pp();   ## call procedure with wrong # of arguments ##
	pp(1);  ## call procedure with wrong # of arguments ##
	pp(1,cc,false,1,1);  ## call procedure with wrong # of arguments ##
	f();  ## can't call a function here ##
	pp(1,'c',false);  ## type of param and actual 'c' mismatch ##
	pp(ii,'c',false);  ## type of param and actual ii, 'c' mismatch ##
	pp(1,cc,0);  ## type of param and actual 0 mismatch ##
}
   
int ff(int i, char c[], bool b) {
	char cc[100];
	int ii[100];
	ff(1,cc,false);  ## can't call a function here ##
	i=ff(1,cc,false);
	i=ff();  ## call function with wrong # of argument ##
	c=ff(1,cc,false);  ## wrong type assignment ##
	b=ff(1,cc,false);  ## wrong type assignmet ##
	i=ff(1);  ## call function with wrong # of argument ##
	i=ff(1,cc,false,1,1);  ## call function with wrong # of argument ##
	i=ff(1,'c',false);  ## type of param and actual 'c' mismatch ##
	i=ff(ii,'c',false); ## type of param and actual ii, 'c' mismatch ##
	i=ff(1,cc,0); ## type of param and actual 0 mismatch ##
}

void jj(int i,int j,int i[]) { ## duplicate declaration jj, i ##
	int ii; 
   	int j;  ## duplicate declaration ##
	int i=10;  ## duplicate declaration ##
	ii=false;  ## wrong type assignment ##
	return;
}

void p(INT j, BooL bb[]) {
	print("Ans = ", j+1, "\n");
}

int f(int i){  ## duplicate declaration ##
        ar[i] = 100;
	suspend();  ## undeclared variable ##
	ReturN i+1;
}

void main(){
	int me;
	read(b);  ## b is not declared ##

	if (b || true) {  ## b is not declared ##
		bool local;
		bool me;
		me = 10;  ## wrong type assignment ##
		me = ! me;
		me = !10;  ## wrong type of operand ##
		i = me+10; ## left operand has wrong type ##
		local = b && me && ja;  ## b is not declared ##
	} else	b = !(b || true||local); ## b, local are not declared ##
	local = false; ## local is not declared ##

	L: while (i != true) { ## wrong type of operand ##
		i = i*i/2; break L;
		i = (int) i/(i-2); continue L;
		i = (int) "abcde";  ## can't cast string lit ##
	}
	me: while (i != '0') { ## me is already declared ##
		i = i*i/2; break main;  ## main is not a label ##
		i = (int) i/(i-2); continue L;  ## L is not label for this loop ##
		break xxx; continue yyy;  ## xxx, yyy are not declared ##
	}

	if (i == 10 || i < 21 || i > ~17 || i != 123) {
		print( i);
		p(17); ## call procedure with wrong # of argument ##
		q();   ## q is not declared ##
		return;
	}

	if (i >= f(~3,10,20) || i <= f(i-1)) ## f has no argument ##
		print( i, f(i), 'Z', ar[123], "\n"); ## f has no argument ##

}
} // That's all folks
