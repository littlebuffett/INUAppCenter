#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_SIZE 100

struct Data
{
	char *key1;
	char *value1;

	char *key2;
	char *value2;
};

int main(void) {
	
	/*    json파일을 열어서 문자열로 저장    */

	FILE* fp;
	fp = fopen("test.json", "rb");

	if (fp == NULL){
		printf("파일 불러오기 실패");
		return NULL;
	}

	int size;
	char * json;

	fseek(fp, 0, SEEK_END); // 데이터 크기 구하기
	size = ftell(fp);
	fseek(fp, 0, SEEK_SET);

	json = malloc(size + 1); //데이터 크기만큼 메모리 동적할당(문자열 맨 마지막에 NULL이 있어야 하기에 +1)
	memset(json, 0, size + 1); //우선 모두 0으로 대입.

	if (json == NULL) {
		printf("메모리 할당 오류");
		return NULL;
	}
	fread(json, 1, size, fp); //파일스트림에 있는 데이터를 문자열 변수 json에 대입.
	
	

	/*    큰 따옴표에 사이에 있는 문자열을 data구조체에 저장    */

	int dataSize = 0; //오브젝트의 수 
	int dataIndex = 0; //data구조체 배열을 컨트롤할 인덱스
	int valueIndex = 0; //0:ke1, 1:value1, 2:key2, 3:value2  
	
	for (int pos = 0; json[pos] != NULL; pos++) {
		if (json[pos] == '{') dataSize++; // '{'의 수 == 오브젝트의 수
	} 
	struct Data *dp = (struct Data*)malloc( sizeof(struct Data) * dataSize ); // 오브젝트 수만큼 데이터 구조체 동적할당.
	
	char * start;
	char * end;
	int stringLength;

	for (int pos = 0; json[pos] != NULL; pos++) {

		if (json[pos] == '"') { // ""사이의 문자열 추출하기

			start = json + pos + 1; 
			end = strchr(start, '"');
			if (end == NULL)    
				break;
		
			stringLength = end - start;

			switch (valueIndex)
			{
			case 0:
				dp[dataIndex].key1 = malloc(stringLength + 1);
				memset(dp[dataIndex].key1, 0, stringLength + 1);
				memcpy(dp[dataIndex].key1, start, stringLength);
				break;
			case 1:	
				dp[dataIndex].value1 = malloc(stringLength + 1);
				memset(dp[dataIndex].value1, 0, stringLength + 1);
				memcpy(dp[dataIndex].value1, start, stringLength); 
				break;
			case 2: 
				dp[dataIndex].key2 = malloc(stringLength + 1);
				memset(dp[dataIndex].key2, 0, stringLength + 1);
				memcpy(dp[dataIndex].key2, start, stringLength); 
				break;
			case 3: 
				dp[dataIndex].value2 = malloc(stringLength + 1);
				memset(dp[dataIndex].value2, 0, stringLength + 1);
				memcpy(dp[dataIndex].value2, start, stringLength); 
				break;
			}

			valueIndex++;
			if (valueIndex == 4) {
				dataIndex++;
				valueIndex = 0;
			}

			pos = pos + stringLength + 1;
		}
	}

	fclose(fp);
	free(json);



	/*    데이터 출력    */

	for (int k = 0; k < dataIndex; k++) {
		printf("%s: %s\n", dp[k].key1, dp[k].value1);
		printf("%s: %s\n", dp[k].key2, dp[k].value2);
		printf("\n");
	}

	return 0;
}