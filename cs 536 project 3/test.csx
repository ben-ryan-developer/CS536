class test {
## sample test prog for proj #3 (parser) --
   may not type-check or execute correctly ##

int i = 0 ## init to 0##;
int j = ~123;
int bigPos = 11111111;
int bigNeg = ~2222222;
bool ja = true;
bool nein = false;
char c = 'c';
char nl = '\n';
char tab = '\t';
char bslash = '\\';
char tic = '\'';
CONST ten = 10;
int ar[100];

void p(INT j, BooL bb[]) {##
	##print("Ans = ", j+1, "\n");
}

int f(int i,int j){
        ar[i] = 100;
	suspend();
        i++;
	j--;
	ReturN i+j;
}

void main(){
	read(b);

	if (b || true) {
		bool local;
		local = b && false;
	} else	b = !(b || true);

	L: while (!(i == 0)) {
		i = i*i/2; break L;
		i = (int) i/(i-2); continue L;
	}

	if (i == 10 || i < 21 || i > ~17 || i != 123) {
		print( i);
		p(17);q(); return;
	}

	if (i >= f(~3,10,20) || i <= f(i-1))
		print( i, f(i), 'Z', ar[123], "\n");

}
} // That's all folks
