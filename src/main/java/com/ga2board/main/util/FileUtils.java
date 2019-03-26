package com.ga2board.main.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Post;

@Component("fileUtils")
public class FileUtils {
	private static final String filePath = "C:\\dev\\downloadFile\\";
    
    public List<Attachment> parseInsertFileInfo(Post post, HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Attachment> list = new ArrayList<Attachment>();
//        Map<String, Object> listMap = null;
        Attachment att = null;
        //post의 아이디 받아오기
        String postId = post.getId();
         
        //filePath에 폴더 없으면 폴더 생성
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                System.out.println(">>>"+originalFileName);
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                //transferTo로 지정된 경로에 파일 저장
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                att = new Attachment();
                att.setFilePath(filePath);
                att.setPostId(postId);
                att.setFileOrg(originalFileName);
                att.setFileName(storedFileName);
                att.setSize(multipartFile.getSize());
                list.add(att);
//                listMap = new HashMap<String,Object>();
//                listMap.put("BOARD_IDX", boardIdx);
//                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
//                listMap.put("STORED_FILE_NAME", storedFileName);
//                listMap.put("FILE_SIZE", multipartFile.getSize());
//                list.add(arg0)(listMap);
            }
        }
        return list;
    }
    
//    public List<Attachment> parseUpdateFileInfo(Post post, HttpServletRequest request) throws Exception{
//
//        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
//        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//         
//        MultipartFile multipartFile = null;
//        String originalFileName = null;
//        String originalFileExtension = null;
//        String storedFileName = null;
//         
//        List<Attachment> list = new ArrayList<Attachment>();
//         
//         
////        while(iterator.hasNext()){
////            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
////            if(multipartFile.isEmpty() == false){
////                originalFileName = multipartFile.getOriginalFilename();
////                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
////                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
////                 
////                multipartFile.transferTo(new File(filePath + storedFileName));
////                 
////                listMap = new HashMap<String,Object>();
////                listMap.put("IS_NEW", "Y");
////                listMap.put("BOARD_IDX", boardIdx);
////                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
////                listMap.put("STORED_FILE_NAME", storedFileName);
////                listMap.put("FILE_SIZE", multipartFile.getSize());
////                list.add(listMap);
////            }
////            else{
////                requestName = multipartFile.getName();
////                idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
////                if(map.containsKey(idx) == true && map.get(idx) != null){
////                    listMap = new HashMap<String,Object>();
////                    listMap.put("IS_NEW", "N");
////                    listMap.put("FILE_IDX", map.get(idx));
////                    list.add(listMap);
////                }
////            }
////        }
//        return list;
//    }



}
