//package cn.com.jrj.m.service.cms;
//
//import java.io.StringReader;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//
//import org.springframework.stereotype.Service;
//
//import cn.com.jrj.m.domain.CmsFrag;
//import cn.com.jrj.m.domain.CmsFrags;
//import cn.com.jrj.m.utils.Constant;
//import cn.com.jrj.m.utils.HttpUtil;
//
///**
// * @Description: 碎片处理
// * @author yuhai.li  
// * @date 2018年1月26日 下午2:32:10
// */
//@Service
//public class FragService {
//	
//	/**
//	 * @Description: 从接口获取碎片数据
//	 * @return List<CmsFrag>
//	 * @author yuhai.li
//	 * @date 2018年1月26日 下午6:22:35
//	 */
//	public List<CmsFrag> getFragFromInteface(){
////		"http://cmsdata.jrj.com.cn/outdata/getspecitemxml.jsp?chnmbclsid=611002099,611002100,611002101,611002102,611002103,611002104,611002105,611002106,611002107,611002108,611002109,611002110,611002111,611002112,611002113,611002114,611002115,611002116,611002117,611002118&pagesize=30";
////		1.iiid:碎片ID编号
////		2.chnmbclsid:碎片所在的频道栏目编号组合（3位或者9位，如为3位是指频道编号，9位的话是频道栏目组合）
////		3.pagesize:每页显示几条数据
////		4.curpage:获取哪一页的数据
//		String xml = HttpUtil.get(Constant.SP_ECITE);
//		List<CmsFrag> result = new LinkedList<CmsFrag>();
//		List<CmsFrag> list = null;
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(CmsFrags.class);
//			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//			CmsFrags cmsFragList = (CmsFrags) jaxbUnmarshaller.unmarshal(new StringReader(xml));
//			list = cmsFragList.getList();
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//	添加过滤，过滤需要的碎片，并给出类型，一个碎片为轮播图，其他默认为推广位
//		if(list!=null && list.size()>0){
//			for(CmsFrag cmsFrag:list){
//				if (cmsFrag.getIntor()!=null && !cmsFrag.getIntor().trim().equals("")) {
//					cmsFrag.setType(0);	//	推广位
//					result.add(cmsFrag);
//				}
//			}
//		}
//		return result;
//	}
//
//}
