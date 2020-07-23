#include <vector>
#include <iostream>
#include <caffe/blob.hpp>
using namespace caffe;
using namespace std;
int main(void)
{
	Blob<float> bb;
	cout<<"Size : "<< bb.shape_string()<<endl;
	bb.Reshape(2, 2, 3, 3);
	cout<<"Size : "<< bb.shape_string()<<endl;
	float * p1 = bb.mutable_cpu_data();
	float * p2 = bb.mutable_cpu_diff();
	for(int i = 0; i < bb.count(); i++)
	{
		p1[i] = i;	               // 将 data 初始化为 0，1, 2, 3，……
		p2[i] = bb.count() - 1 - i; // 将 diff 初始化为 35, 34, 33，……
	}

	for(int u = 0; u < bb.num(); u++)
	{
		for(int v = 0; v < bb.channels(); v++)
		{
			for(int w = 0; w < bb.height(); w++)
			{
				for(int x = 0; x < bb.width(); x++)
				{
					cout<<"bb["<<u<<"]["<<v<<"]["<<w<<"]["<<x<<"] = "<< bb.data_at(u, v, w, x)<<endl;
				}
			}
		}
	} 

	cout<<"ASUM = "<<bb.asum_data()<<endl;
	cout<<"SUMSQ = "<<bb.sumsq_data()<<endl;


	bb.Update();// 执行 Update 操作

	for(int u = 0; u < bb.num(); u++)
	{
		for(int v = 0; v < bb.channels(); v++)
		{
			for(int w = 0; w < bb.height(); w++)
			{
				for(int x = 0; x < bb.width(); x++)
				{
					cout<<"bb["<<u<<"]["<<v<<"]["<<w<<"]["<<x<<"] = "<< bb.data_at(u, v, w, x)<<endl;
				}
			}
		}
	} 

	cout<<"ASUM = "<<bb.asum_data()<<endl;
	cout<<"SUMSQ = "<<bb.sumsq_data()<<endl;

	return 0;
}
